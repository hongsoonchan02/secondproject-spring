package kr.co.secondProject.vacation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import kr.co.secondProject.vacation.dto.ReqVacationDTO;
import kr.co.secondProject.vacation.dto.ResVacationDTO;
import kr.co.secondProject.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Tag(name="휴가 관리API", description="휴가관리 관련API")
@RestController
@RequestMapping("/api/vacation")
@RequiredArgsConstructor
public class VacationController {
	private final VacationService vacationService;
	
	/*
	 * 휴가 관리 페이지 
	 */
	
	@Operation(
			summary = "로그인 유저 휴가신청 통계 조회",
			description = "로그인ID로 해당 유저의 휴가 신청 통계를 조회합니다."
			)
	
	// 로그인 유저 휴가 통계 
	@GetMapping("/summary")
    public ResponseEntity<ResVacationDTO> getSummary(HttpSession session) {
        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(vacationService.getVacationSummary(loginId));
    }
	
	@Operation(
			summary = "로그인 유저 휴가신청 목록 조회",
			description = "로그인ID로 해당 유저의 휴가 신청 목록을 조회합니다."
			)
	
	// 로그인 유저 휴가 신청 내역
	@GetMapping("/my-history")
    public ResponseEntity<Page<ResVacationDTO>> getMyHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpSession session) {

        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vacationService.getMyVacationHistory(loginId, pageable));
    }

	// [세션 유틸]세션에서 로그인 유저 pk 반환
	private Long getLoginId(HttpSession session) {
        return (Long) session.getAttribute("loginEmployeeId");
    }
	
	@Operation(
			summary = "관리자 휴가 승인 대기열",
			description = "본인을 제외한 모든 유저의 휴가 신청 목록을 조회합니다."
			)
	// 휴가 신청 대기열 목록 (관리자 등급)
	@GetMapping("/pending")
    public ResponseEntity<Page<ResVacationDTO>> getPendingQueue(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            HttpSession session) {

        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (!isManagerOrAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vacationService.getPendingQueue(loginId, pageable));
    }
	
	// [세션 유틸]유저 등급 확인
	private boolean isManagerOrAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "ADMIN".equals(role) || "MANAGER".equals(role);
    }
	
	
	// -----------------------------------------------------------------------------------------------------------------------------
    
    /*
     * 휴가 신청 페이지
     */
	
	@Operation(
			summary = "휴가 신청작성",
			description = "휴가 신청서를 작성하는 기능, 대리신청은 관리자 매니저만 가능"
			)
	@PostMapping("/request")
    public ResponseEntity<ResVacationDTO> requestVacation(
            @RequestBody ReqVacationDTO dto,
            HttpSession session) {

        Long loginId = getLoginId(session);
        if (loginId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // 대리 신청은 관리자 / 매니저만 가능
        boolean isProxy = dto.getProxyEmpId() != null && !dto.getProxyEmpId().isBlank();
        if (isProxy && !isManagerOrAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ResVacationDTO result = vacationService.requestVacation(loginId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
