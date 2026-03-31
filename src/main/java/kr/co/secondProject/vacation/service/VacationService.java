package kr.co.secondProject.vacation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.secondProject.vacation.dto.ResVacationDTO;

public interface VacationService {

	
	// 로그인 직원의 휴가 신청 이력 불러오기
	Page<ResVacationDTO> getMyVacationHistory(Long employeeId, Pageable pageable);
	
	
	
}
