package kr.co.secondProject.vacation.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.secondProject.vacation.dto.ReqVacationDTO;
import kr.co.secondProject.vacation.dto.ResVacationDTO;

public interface VacationService {

	/*
	 * 휴가 관리 페이지 
	 */
	
	// 로그인 유저의 휴가 신청 이력 불러오기
	Page<ResVacationDTO> getMyVacationHistory(Long employeeId, Pageable pageable);
	
	// 로그인 유저의 휴가 신청 통계
	ResVacationDTO getVacationSummary(Long employeeId);
	
	// 휴가 신청 대기열 조회 (관리자 등급)
	Page<ResVacationDTO> getPendingQueue(Long loginEmployeeId, Pageable pageable);
	
	// -----------------------------------------------------------------------------------------------------------------------------
    
    /*
     * 휴가 신청 페이지
     */
	
	ResVacationDTO requestVacation(Long loginEmployeeId, ReqVacationDTO dto);
	
	
	// -----------------------------------------------------------------------------------------------------------------------------

    /*
     * 연차 관리 페이지 (관리자 등급)
     */
	

    // 연차 관리 이력 통계 카드 데이터
    ResVacationDTO getVacationListStats(LocalDateTime lastCheckedAt);

    // 탭별 휴가 목록 페이징 조회
    Page<ResVacationDTO> getVacationListByKind(String kind, Pageable pageable);

    // 휴가 승인
    ResVacationDTO approveVacation(Long vacationCode);

    // 휴가 반려
    ResVacationDTO rejectVacation(Long vacationCode);
	
	
}
