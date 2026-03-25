package kr.co.secondProject.department.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentUpdateDTO;
import kr.co.secondProject.department.entity.Department;
import kr.co.secondProject.department.repository.DepartmentRepository;
import kr.co.secondProject.department.service.DepartmentService;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
	
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	
	// 부서 리스트 조회
	public List<ResDepartmentListDTO> departmentList() {
		List<Department> dpList = departmentRepository.findAll();
		
		List<ResDepartmentListDTO> response = dpList.stream()
				.map(department -> ResDepartmentListDTO.builder()
						.dpCode(department.getDpCode())
						.dpName(department.getDpName())
						.dpDetail(department.getDpDetail())
						.dpManager(department.getDpManager().getName())
						.dpMember(employeeRepository.countByDepartment(department))//.부서에 맞는 인원 로직이 담긴 명령어
						.build())
				.collect(Collectors.toList());
		
		return response;
	}
	
	// 부서 검색
	public List<ResDepartmentListDTO> departmentSerch(String keyword) {
			List<Department> searchList = departmentRepository.findByDpNameContaining(keyword);
			List<ResDepartmentListDTO> response = searchList.stream()
					.map(search -> ResDepartmentListDTO.builder()
							.dpCode(search.getDpCode())
							.dpName(search.getDpName())
							.dpDetail(search.getDpDetail())
							.dpManager(search.getDpManager().getName())
							.dpMember(employeeRepository.countByDepartment(search))
							.build())
					.collect(Collectors.toList());
		return response;
	}
	
	// 부서 관리(수정)
	public ResDepartmentUpdateDTO departmentUpdate(Long id, ReqDepartmentUpdateDTO request) {
		Department updated = departmentRepository.findById(id).orElse(null);
		if (updated != null) {
			                                      // EmployeeRepository에 구현해야함
			Employee empEntity = employeeRepository.findByEmpId(request.getDpManagerEmpNum()).orElse(null);
			updated.update(request.getDpName(), request.getDpDetail(), empEntity);
			Department saveData = departmentRepository.save(updated);
			ResDepartmentUpdateDTO response = ResDepartmentUpdateDTO.builder()
					.dpCode(saveData.getDpCode())
					.dpName(saveData.getDpName())
					.dpNum(saveData.getDpNum())
					.dpDetail(saveData.getDpDetail())
					.dpManager(saveData.getDpManager().getName())
					.build();
			return response;
			
		}
	}
	
	// 부서 관리(삭제)
	public void departmentDelete(Long id) {
		Department department = departmentRepository.findById(id).orElse(null);
		if (department != null) {
			departmentRepository.delete(department);			
		}
		
	}

}
