package kr.co.secondProject.vacation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Tag(name="휴가 관리API", description="휴가관리 관련API")
@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
public class VacationController {
	private final VacationService vacationService;
	
	// 유저 휴가 통계 
	@GetMapping("/summary")
    public ResponseEntity<ResVacationDTO> getSummary(HttpSession session) {
        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(vacationService.getVacationSummary(loginId));
    }
	
	// 휴가 관리 신청 내역
	@GetMapping("/")
    public ResponseEntity<Page<ResVacationDTO>> getMyHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpSession session) {

        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vacationService.getMyVacationHistory(loginId, pageable));
    }


	private Long getLoginId(HttpSession session) {
        return (Long) session.getAttribute("loginEmployeeId");
    }
	

}
