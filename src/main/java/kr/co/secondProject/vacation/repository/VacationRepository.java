package kr.co.secondProject.vacation.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.secondProject.vacation.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>{
	
	
	
	// 로그인 유저 휴가 신청 대기 건수
	long countByEmployeeIdAndApprovalIsNull(Long employeeId);

	
	// 로그인 직원의 휴가 신청 내역 조회
	Page<Vacation> findByEmployeeIdOrderByStartTimeDesc(Long employeeId, Pageable pageable);
		
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
	
}
