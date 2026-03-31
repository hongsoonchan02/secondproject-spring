package kr.co.secondProject.vacation.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.secondProject.vacation.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>{
	
	// 로그인 직원의 휴가 신청 내역 조회
	Page<Vacation> findByEmployeeIdOrderByStartTimeDesc(Long employeeId, Pageable pageable);
}
