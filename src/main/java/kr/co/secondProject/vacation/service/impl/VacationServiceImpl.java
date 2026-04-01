package kr.co.secondProject.vacation.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.repository.VacationRepository;
import kr.co.secondProject.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;



@Service  // 빈 등록 
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	
	private final VacationRepository vacationrepository;
	
	
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
                    /*
                     * TODO: employeeRepository.findById(v.getEmployeeId()) 로
                     *       신청자 이름·직책·프로필 사진을 채워주세요.
                     */
                    return dto;
                });
    }
    

}
