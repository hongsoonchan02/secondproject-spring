package kr.co.secondProject.vacation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Tag(name="휴가 관리API", description="휴가관리 관련API")
@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
public class VacationController {
	private final VacationService vacationService;
	
	// [GET] 특정 직원 휴가 신청 전체 조회
	@GetMapping("/{employeeId")
	public ResponseEntity<List<ResVacationDTO>> getVacationList(
			@PathVariable Long employeeId){
		return ResponseEntity.ok(vacationService.getVacationList(employeeId));
	}
	
	

}
