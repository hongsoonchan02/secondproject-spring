package kr.co.secondProject.vacation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.EmployeeRepository;
import kr.co.secondProject.vacation.dto.ReqVacationDTO;
import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.entity.Vacation;
import kr.co.secondProject.vacation.repository.VacationRepository;
import kr.co.secondProject.vacation.service.VacationService;
import lombok.*;


@Service  // 빈 등록 
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	
	private final VacationRepository vacationrepository;
	private final EmployeeRepository employeerepository;
	/*
	 * 휴가 관리 페이지 
	 */
	
	// 로그인 유저 휴가 신청 이력 조회
    @Override
    @Transactional(readOnly = true)
    public Page<ResVacationDTO> getMyVacationHistory(Long employeeId, Pageable pageable) {
        return vacationrepository
                .findByEmployeeIdOrderByStartTimeDesc(employeeId, pageable)
                .map(ResVacationDTO::from);
    }
    
    
    // 로그인 유저 휴가 사용 통계
    @Override
    @Transactional(readOnly = true)
    public ResVacationDTO getVacationSummary(Long employeeId) {
    	int currentYear = LocalDate.now().getYear();

        double totalAnnual = 15.0;
        double usedAnnual  = vacationrepository
                .sumUsedVacationByYear(employeeId, currentYear);
        double remaining   = totalAnnual - usedAnnual;

        long pendingCount = vacationrepository.countByEmployeeIdAndApprovalIsNull(employeeId);

        // 잔여 연차 유효기간: 해당 연도 마지막 날
        String annualExpiry = currentYear + "년 12월 31일";

        return ResVacationDTO.builder()
                .remaining(remaining)
                .usedAnnual(usedAnnual)
                .pendingCount(pendingCount)
                .annualExpiry(annualExpiry)
                .build();
    }
    
    // 휴가 신청 대기열(휴가 관리 페이지_관리자 등급)
    @Override
    @Transactional(readOnly = true)
    public Page<ResVacationDTO> getPendingQueue(Long loginEmployeeId, Pageable pageable) {
        LocalDate now = LocalDate.now();
        return vacationrepository
                .findPendingQueueExcludeMe(loginEmployeeId, now.getYear(), now.getMonthValue(), pageable)
                .map(vacation -> {
                    ResVacationDTO dto = ResVacationDTO.from(vacation);

                    return dto;
                });
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------
    
    /*
     * 휴가 신청 페이지
     */
    
    
    // 휴가 신청
    @Override
    @Transactional
    public ResVacationDTO requestVacation(Long loginEmployeeId, ReqVacationDTO dto) {

        // ── 유효성 검증 ──────────────────────────────────────────

        // 기타 선택 시 상세 사유 필수
        if ("기타".equals(dto.getKind()) &&
                (dto.getReason() == null || dto.getReason().isBlank())) {
            throw new IllegalArgumentException("기타 사유를 선택한 경우 상세 사유를 입력해야 합니다.");
        }

        // 사용 일수 허용 값 검증 (0.5, 1, 2, 3, 4, 5)
        double[] allowed = {0.5, 1.0, 2.0, 3.0, 4.0, 5.0};
        boolean validDays = false;
        for (double d : allowed) {
            if (Double.compare(dto.getUsedDays(), d) == 0) {
                validDays = true;
                break;
            }
        }
        if (!validDays) {
            throw new IllegalArgumentException("사용 일수는 0.5, 1, 2, 3, 4, 5일 중 하나여야 합니다.");
        }

        // ── 대리 신청 여부 판단 ─────────────────────────────────
        boolean isProxy       = dto.getProxyEmpId() != null && !dto.getProxyEmpId().isBlank();
        long targetEmpId  = isProxy ? dto.getTargetEmployeeId() : loginEmployeeId; 
        String  proxyEmpId    = isProxy ? dto.getProxyEmpId() : null;

        // ── 잔여 연차 검증 ──────────────────────────────────────
        int currentYear = LocalDate.now().getYear();
        double totalAnnual = 15.0; 
        double usedAnnual  = vacationrepository.sumUsedVacationByYear(targetEmpId, currentYear);
        double remaining   = totalAnnual - usedAnnual;

        if (dto.getUsedDays() > remaining) {
            throw new IllegalStateException(
                    "잔여 연차(" + remaining + "일)가 부족합니다. 요청 일수: " + dto.getUsedDays());
        }

        // ── 종료일 계산 ─────────────────────────────────────────
        LocalDateTime endTime;
        if (Double.compare(dto.getUsedDays(), 0.5) == 0) {
            endTime = dto.getStartTime();
        } else {
            endTime = dto.getStartTime().plusDays((long) (dto.getUsedDays() - 1));
        }

        // ── 엔티티 저장 ─────────────────────────────────────────
        Vacation vacation = Vacation.builder()
					                .startTime(dto.getStartTime())
					                .endTime(endTime)
					                .remaining((double) Math.round(dto.getUsedDays() * 2)) // 0.5 단위 → 정수로 보관
					                .approval(null)          // 요청됨 (대기)
					                .kind(dto.getKind())
					                .annualCode(dto.getAnnualCode())
					                .employeeId(targetEmpId)
					                .proxyEmpId(proxyEmpId)
					                .reason(dto.getReason())
					                .build();
        
        
        return ResVacationDTO.from(vacation);
    }
    
	// -----------------------------------------------------------------------------------------------------------------------------
    
    /*
     * 연차 관리 페이지 (관리자 등급)
     */
    
    // 휴가 신청 통계
    @Override
    @Transactional(readOnly = true)
    public ResVacationDTO getVacationListStats(LocalDateTime lastCheckedAt) {
        LocalDate now = LocalDate.now();

        long totalPending           = vacationrepository.countByApprovalIsNull();
        long totalApproved          = vacationrepository.countByApprovalTrue();
        long totalRejected          = vacationrepository.countByApprovalFalse();
        long thisMonthVacationCount = vacationrepository
                .countDistinctEmployeeOnVacationThisMonth(now.getYear(), now.getMonthValue());
        long newRequestCount        = lastCheckedAt != null
                ? vacationrepository.countByApprovalIsNullAndStartTimeAfter(lastCheckedAt)
                : 0L;

        return ResVacationDTO.builder()
                .totalPending(totalPending)
                .totalApproved(totalApproved)
                .totalRejected(totalRejected)
                .thisMonthVacationCount(thisMonthVacationCount)
                .newRequestCount(newRequestCount)
                .build();
    }
    
    // 탭별 휴가 목록 페이징 조회
    @Override
    @Transactional(readOnly = true)
    public Page<ResVacationDTO> getVacationListByKind(String kind, Pageable pageable) {
        boolean isAll = kind == null || kind.isBlank() || "전체".equals(kind);
        Page<Vacation> page = isAll
                ? vacationrepository.findAllByOrderByStartTimeDesc(pageable)
                : vacationrepository.findByKindOrderByStartTimeDesc(kind, pageable);

        return page.map(v -> {
            ResVacationDTO dto = ResVacationDTO.from(v);
            
            Employee employee = employeerepository.findById(v.getEmployeeId())
                    .orElse(null);

            if (employee != null) {
                return ResVacationDTO.builder()
                        .vacationCode(dto.getVacationCode())
                        .startTime(dto.getStartTime())
                        .endTime(dto.getEndTime())
                        .remaining(dto.getRemaining())
                        .approval(dto.getApproval())
                        .approvalLabel(dto.getApprovalLabel())
                        .kind(dto.getKind())
                        .reason(dto.getReason())
                        .empId(employee.getEmpId())
                        .employeeName(employee.getName())
                        .position(employee.getPosition())
                        .build();
            }
            
            
            return dto;
        });
    }
    
    
    // 휴가 신청 승인
    @Override
    @Transactional
    public ResVacationDTO approveVacation(Long vacationCode) {
        Vacation vacation = getVacationOrThrow(vacationCode);
        vacation.setApproval(true);
        return ResVacationDTO.from(vacationrepository.save(vacation));
    }

    // 휴가 신청 반려
    @Override
    @Transactional
    public ResVacationDTO rejectVacation(Long vacationCode) {
        Vacation vacation = getVacationOrThrow(vacationCode);
        vacation.setApproval(false);
        return ResVacationDTO.from(vacationrepository.save(vacation));
    }


    // 잔여 휴가 조회
    private Vacation getVacationOrThrow(Long vacationCode) {
        return vacationrepository.findById(vacationCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 휴가 신청을 찾을 수 없습니다. vacationCode=" + vacationCode));
    }
    

}
