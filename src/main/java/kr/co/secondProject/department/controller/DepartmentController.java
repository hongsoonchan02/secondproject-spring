package kr.co.secondProject.department.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResCurrentDpListDTO;
import kr.co.secondProject.department.dto.ResDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ResDepartmentDetailDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResUpdateMemberListDTO;
import kr.co.secondProject.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@Tag(name = "Department Api", description = "부서 관리 기능")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	@Operation(summary = "메인 화면", description = "헤더, 부서 리스트 조회 및 검색")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "조회 성공"
		), 
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@GetMapping
	public ResponseEntity<List<ResDepartmentListDTO>> show(
			@Parameter(
					name = "keyword",
					description = "검색 키워드 (없으면 전체 조회)",
					required = false)@RequestParam(value = "keyword", required = false) String keyword) {
	
		List<ResDepartmentListDTO> dpConstruct = departmentService.departmentList(keyword);
		
		return ResponseEntity.status(HttpStatus.OK).body(dpConstruct);
	}

	@Operation(summary = "부서 생성", description = "정보 입력후 생성")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"
				),
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@PostMapping
	public ResponseEntity<ResDepartmentCreateDTO> create(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "부서 생성 요청 데이터",
					required = true
					)
			@RequestBody ReqDepartmentCreateDTO dto) {
		
		ResDepartmentCreateDTO response = departmentService.departmentCreate(dto);
		
		return (response != null) ?
				ResponseEntity.status(HttpStatus.OK).body(response) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@Operation(summary = "최근 생성 부서 리스트", description = "부서 생성탭 최근 생성 부서 리스트")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"
				),
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@GetMapping("/recent")
	public ResponseEntity<List<ResCurrentDpListDTO>> currentCreateDpList() {
		List<ResCurrentDpListDTO> responseList = departmentService.currentDpList();

		// 실패랄게 없다 데이터가 없으면 그냥 빈 문자열 [] 반환
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}
	
	@Operation(summary = "부서 수정탭 부서원 리스트", description = "부서 id로 부서원 리스트 조회")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"
				),
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@GetMapping("/members/{id}")
	public ResponseEntity<List<ResUpdateMemberListDTO>> dpMemberList(
			@Parameter(
					description = "부서 번호",
					required = true
					)
			@PathVariable("id") Long id) {
		
		List<ResUpdateMemberListDTO> responseList = departmentService.updateList(id);
		
		return (responseList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(responseList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@Operation(summary = "부서 수정", description = "부서 id로 조회 후 수정")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"),
		@ApiResponse(
				responseCode = "200",
				description = "잘못된 요청"
				)
	})
	@PatchMapping("/{id}")
	public ResponseEntity<ResDepartmentUpdateDTO> update(
			@Parameter(
					description = "부서 번호",
					required = true
					)
			@PathVariable("id") Long id, 
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "부서 수정 정보",
					required = true
					)
			@RequestBody ReqDepartmentUpdateDTO dto) {
		ResDepartmentUpdateDTO response = departmentService.departmentUpdate(id, dto);
		
		return (response != null) ?
				ResponseEntity.status(HttpStatus.OK).body(response) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	@Operation(summary = "부서 조회", description = "특정 부서 조회용")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"
				),
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@GetMapping("/{id}")
	public ResponseEntity<ResDepartmentDetailDTO> detail(
			@Parameter(
					description = "부서 번호",
					required = true
					)
			@PathVariable("id") Long id) {
		ResDepartmentDetailDTO response = departmentService.departmentDetail(id);
		
		return (response != null) ?
				ResponseEntity.status(HttpStatus.OK).body(response) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@Operation(summary = "부서 삭제", description = "부서 id에 맞는 부서 삭제")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "성공"
				),
		@ApiResponse(
				responseCode = "400",
				description = "잘못된 요청"
				)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(
			@Parameter(
					description = "부서 번호",
					required = true
					)
			@PathVariable("id") Long id) {
		departmentService.departmentDelete(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
	}
}















