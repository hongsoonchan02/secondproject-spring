package kr.co.secondProject.vacation.repository;


import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.secondProject.vacation.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>{
	
	/*
	 * 휴가 관리 페이지 
	 */
	
	// 로그인 유저 휴가 신청 대기 건수
	long countByEmployeeIdAndApprovalIsNull(Long employeeId);

	/**
	 * 해당 연도에 승인된 사용 휴가 합계
	 * ※해당 기능을 react 처리시 보안상 안 좋음
	 */
	@Query("""
            SELECT COALESCE(SUM(v.remaining), 0)
            FROM Vacation v
            WHERE v.employeeId = :employeeId
              AND v.approval = true
              AND YEAR(v.startTime) = :year
            """)
	Double sumUsedVacationByYear(@Param("employeeId") Long employeeId,
			@Param("year") int year);
	
	
	// 로그인 직원의 휴가 신청 내역 조회
	Page<Vacation> findByEmployeeIdOrderByStartTimeDesc(Long employeeId, Pageable pageable);
	
	// 다른 유저 휴가 신청 대기열 목록(관리자 등급)
	@Query("""
            SELECT v FROM Vacation v
            WHERE v.employeeId <> :loginEmployeeId
              AND v.approval IS NULL
              AND YEAR(v.startTime) = :year
              AND MONTH(v.startTime) = :month
            ORDER BY v.startTime ASC
            """)
    Page<Vacation> findPendingQueueExcludeMe(@Param("loginEmployeeId") Long loginEmployeeId,
                                             @Param("year") int year,
                                             @Param("month") int month,
                                             Pageable pageable);
	
	
	// -----------------------------------------------------------------------------------------------------------------------------
    

    /*
     * 연차 관리 페이지 (관리자 등급)
     */
	
	// 휴가 신청 대기
	long countByApprovalIsNull();

	// 휴가 신청 승인
    long countByApprovalTrue();

    // 휴가 신청 반려
    long countByApprovalFalse();

    // 휴가 인원
    @Query("""
            SELECT COUNT(DISTINCT v.employeeId)
            FROM Vacation v
            WHERE YEAR(v.startTime) = :year
              AND MONTH(v.startTime) = :month
              AND v.approval = true
            """)
    long countDistinctEmployeeOnVacationThisMonth(@Param("year") int year,
                                                  @Param("month") int month);
	
    // 신규 신청
    long countByApprovalIsNullAndStartTimeAfter(LocalDateTime lastCheckedAt);
	
    // 전체 휴가 목록 페이징 조회
    Page<Vacation> findAllByOrderByStartTimeDesc(Pageable pageable);
    
    // 사유별 휴가 목록 페이징 조회
    Page<Vacation> findByKindOrderByStartTimeDesc(String kind, Pageable pageable);
    
}
