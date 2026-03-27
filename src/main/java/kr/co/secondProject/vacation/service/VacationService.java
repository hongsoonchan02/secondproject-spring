package kr.co.secondProject.vacation.service;

import java.util.List;

import kr.co.secondProject.vacation.dto.ResVacationDTO;

public interface VacationService {

	
	// 특정 직원의 이번달 전체 휴가 이력 불러오기
	List<ResVacationDTO> getVacationList(Long employeeId);
	// 특정 직원의 이번달 휴가 신청 통계 불러오기
	
	
	
	// 모든 직원의 이번달 휴가 신청 대기열 불러오기
	// 모든 직원의 이번달 휴가 신청 대기열 통계 불러오기
	
	// 휴가 신청
	
	
	
}
