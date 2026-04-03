package kr.co.secondProject.department.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.department.customException.DepartmentException;
import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResCurrentDpListDTO;
import kr.co.secondProject.department.dto.ResDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ResDepartmentDetailDTO;
import kr.co.secondProject.department.dto.ResDepartmentListDTO;
import kr.co.secondProject.department.dto.ResDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResUpdateMemberListDTO;
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
	
	// 부서 생성
	@Transactional
	@Override
	public ResDepartmentCreateDTO departmentCreate(ReqDepartmentCreateDTO dto) {
		//부서 생성 기능 부분                                      										// 찾는 데이터가 없을때(사번으로 직원employee 엔티티)
		Employee manager = employeeRepository.findByEmpId(dto.getDpManagerEmpId()).orElseThrow(() -> new DepartmentException("직원을 찾을 수 없습니다"));
		Department dpEntity = Department.of(dto, manager);
		Department responseEntity = departmentRepository.save(dpEntity);
		
		return ResDepartmentCreateDTO.from(responseEntity);
	}
	
	// 최근 생성 부서 목록
	public List<ResCurrentDpListDTO> currentDpList() {                                  // 부서 값이 없는건 정상적인 일이기 때문에
		 																				// 에러가 안 나게 빈 문자열 반환
		List<Department> dpList = departmentRepository.findTop5ByOrderByDpNumDesc().orElse(Collections.emptyList());
		List<ResCurrentDpListDTO> responseList = dpList.stream()
				.map(ResCurrentDpListDTO :: from)
				.collect(Collectors.toList());
		
		return responseList;
	}
	
	// 부서 리스트 조회 및 검색
		@Transactional(readOnly = true)
		@Override
		public List<ResDepartmentListDTO> departmentList(String keyword) {
			
			List<ResDepartmentListDTO> response;
			
			// 부서 리스트 조회	(검색)	
			if (keyword == null || keyword.isBlank()) {
				List<Department> dpList = departmentRepository.findAll();
				response = dpList.stream()
						.map(ResDepartmentListDTO :: from)
						.collect(Collectors.toList());
				
			} else {
				List<Department> dpList = departmentRepository.findByDpNameContaining(keyword);
				response = dpList.stream()
						.map(ResDepartmentListDTO :: from)
						.collect(Collectors.toList());
			}
			return response;
		}
	
	// 부서 관리(수정)
	@Transactional
	@Override
	public ResDepartmentUpdateDTO departmentUpdate(Long id, ReqDepartmentUpdateDTO request) {
		// 부서 수정 기능                                                            	// 수정하고자 하는 부서를 못 찾을때
		Department updated = departmentRepository.findById(id).orElseThrow(() -> new DepartmentException("찾는 부서가 없습니다."));
																						// 잘못된 인자가 들어왔을때(부서 코드)
		Employee empEntity = employeeRepository.findByEmpId(request.getDpManagerEmpId()).orElseThrow(() -> new DepartmentException("잘못된 부서 코드입니다."));
		updated.update(request.getDpName(), request.getDpDetail(), empEntity);
		ResDepartmentUpdateDTO responseUpdate = ResDepartmentUpdateDTO.from(updated);
		
		return responseUpdate;
		
		}
	
	// 부서 정보 조회
	@Transactional(readOnly = true)
	@Override
	public ResDepartmentDetailDTO departmentDetail(Long id) {
		Department updateDetail = departmentRepository.findById(id).orElseThrow(() -> new DepartmentException("찾는 부서가 없습니다."));
		ResDepartmentDetailDTO detail = ResDepartmentDetailDTO.from(updateDetail);
		return detail;
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public List<ResUpdateMemberListDTO> updateList(Long id) {
		// 해당 부서 부서원 목록 조회
		List<Employee> employee = employeeRepository.findByDpNum(id); // EmployeeRepository 부서 코드 FK에 해당하는 부서원만 찾기
		List<ResUpdateMemberListDTO> responseMember = employee.stream()
				.map(ResUpdateMemberListDTO :: from)
				.collect(Collectors.toList());
		
		return responseMember;
	}
	
	// 부서 관리(삭제)
	@Transactional
	@Override
	public void departmentDelete(Long id) {
		Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentException("삭제할 부서가 없습니다."));
		
		departmentRepository.delete(department);		
	}

}
