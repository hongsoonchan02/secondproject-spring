package kr.co.secondProject.vacation.repository;

import java.util.List;

import kr.co.secondProject.vacation.entity.Vacation;

public class VacationRepository {
	
	// 특정 직원의 휴가 신청 내역 조회
	List<Vacation> list = vacationrepository.finByEmployee_IdOrderByDateDesc(Long employeeId);
}
