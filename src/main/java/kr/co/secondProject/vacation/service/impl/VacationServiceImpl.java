package kr.co.secondProject.vacation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // proxyEmpId 가 있으면 대리 신청 → 대상자는 dto.getTargetEmployeeId()
        // 없으면 본인 신청 → 대상자는 loginEmployeeId
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
        // 0.5일(반차)은 당일 종료, 그 외에는 시작일 + (usedDays - 1) 일
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
    
    
    

}
