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

import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResCurrentDpListDTO;
import kr.co.secondProject.department.dto.ResDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResHeaderDTO;
import kr.co.secondProject.department.dto.ResShowDTO;
import kr.co.secondProject.department.dto.ResUpdateMemberListDTO;
import kr.co.secondProject.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmemtController {
	
	private final DepartmentService departmentService;
	
	@GetMapping
	public ResponseEntity<ResShowDTO> show() {
	
		List<ResDepartmentListDTO> dpList = departmentService.departmentList();
		ResHeaderDTO headerResponse = departmentService.header();
		ResShowDTO show = ResShowDTO.builder()
				.dpList(dpList)
				.header(headerResponse)
				.build();
		
		return (dpList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(show) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ResDepartmentListDTO>> search(@RequestParam String keyword) {
		
		List<ResDepartmentListDTO> searchList = departmentService.departmentSerch(keyword);
		
		return (searchList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(searchList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("/create")
	public ResponseEntity<ResDepartmentCreateDTO> create(@RequestBody ReqDepartmentCreateDTO dto) {
		ResDepartmentCreateDTO response = departmentService.departmentCreate(dto);
		
		return (response != null) ?
				ResponseEntity.status(HttpStatus.OK).body(response) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/create")
	public ResponseEntity<List<ResCurrentDpListDTO>> currentCreateDpList() {
		List<ResCurrentDpListDTO> responseList = departmentService.currnetDpList();
		
		// 실패랄게 없다 데이터가 없으면 그냥 빈 문자열 [] 반환
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}
	
	@GetMapping("/update/{id}")
	public ResponseEntity<List<ResUpdateMemberListDTO>> dpMemberList(@PathVariable Long id) {
		
		List<ResUpdateMemberListDTO> responseList = departmentService.updateList(id);
		
		return (responseList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(responseList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<ResDepartmentUpdateDTO> update(@PathVariable Long id, @RequestBody ReqDepartmentUpdateDTO dto) {
		ResDepartmentUpdateDTO response = departmentService.departmentUpdate(id, dto);
		
		return (response != null) ?
				ResponseEntity.status(HttpStatus.OK).body(response) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		departmentService.departmentDelete(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
	}
}















