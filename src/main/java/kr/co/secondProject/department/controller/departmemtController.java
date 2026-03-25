package kr.co.secondProject.department.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class departmemtController {
	
	private final DepartmentService departmentService;
	
	@GetMapping
	public ResponseEntity<List<ResDepartmentListDTO>> show() {
	
		List<ResDepartmentListDTO> dpList = departmentService.departmentList();
		
		return (dpList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(dpList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ResDepartmentListDTO>> search(@RequestParam String keyword) {
		
		List<ResDepartmentListDTO> searchList = departmentService.departmentSerch(keyword);
		
		return (searchList != null) ?
				ResponseEntity.status(HttpStatus.OK).body(searchList) :
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}















