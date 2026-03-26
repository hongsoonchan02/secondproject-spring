package kr.co.secondProject.department.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.department.dto.ReqDepartmentUpdateDTO;
import kr.co.secondProject.department.dto.ResCurrentDpListDTO;
import kr.co.secondProject.department.dto.ResDepartmentCreateDTO;
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
	public ResDepartmentCreateDTO departmentCreate(ReqDepartmentCreateDTO dto) {
		                                     // EmployeeRepository에서 구현해야함                         // 찾는 데이터가 없을때(사번으로 직원employee 엔티티)
		Employee manager = employeeRepository.findByEmpNum(dto.getDpManagerEmpNum()).orElseThrow(() -> new NoSuchElementException("직원을 찾을 수 없습니다"));
		Department dpEntity = Department.builder()
				.dpCode(dto.getDpCode())
				.dpName(dto.getDpName())
				.dpDetail(dto.getDpDetail()) 
				.dpManager(manager)
				.build();
		Department responseEntity = departmentRepository.save(dpEntity);
		
		return ResDepartmentCreateDTO.from(responseEntity);
	}
	
	// 부성 생성탭 최근 생성 부서 목록
	@Transactional
	public List<ResCurrentDpListDTO> currnetDpList() {
		List<Department> dpList = departmentRepository.findTop5ByorderByIdDesc();
				
		List<ResCurrentDpListDTO> responseList = dpList.stream()
				.map(department -> ResCurrentDpListDTO.builder()
						.dpCode(department.getDpCode())
						.dpName(department.getDpName())
						.build())
				.collect(Collectors.toList());
		
		return responseList;
	}
	
	// 부서 리스트 조회
	@Transactional
	public List<ResDepartmentListDTO> departmentList() {
		List<Department> dpList = departmentRepository.findAll();
		
		List<ResDepartmentListDTO> response = dpList.stream()
				.map(department -> ResDepartmentListDTO.builder()
						.dpCode(department.getDpCode())
						.dpName(department.getDpName())
						.dpDetail(department.getDpDetail())
						.dpManager(Optional.ofNullable(department.getDpManager())
								.map(manager -> manager.getName())
								.orElse(null))
						.dpMember(employeeRepository.countByDepartment(department))// 부서에 맞는 인원 로직이 담긴 명령어 아직 구현 안함.
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
							.dpManager(Optional.ofNullable(search.getDpManager())
									.map(manager -> manager.getName())
									.orElse(null))
							.dpMember(employeeRepository.countByDepartment(search))
							.build())
					.collect(Collectors.toList());
		return response;
	}
	
	// 부서 관리(수정)
	@Transactional
	public ResDepartmentUpdateDTO departmentUpdate(Long id, ReqDepartmentUpdateDTO request) {
		                                                                               // 수정하고자 하는 부서를 못 찾을때
		Department updated = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 부서가 없습니다."));
			                                      // EmployeeRepository에 구현해야함                                // 잘못된 인자가 들어왔을때(부서 코드)
			Employee empEntity = employeeRepository.findByEmpId(request.getDpManagerEmpNum()).orElseThrow(() -> new IllegalArgumentException("잘못된 부서 코드입니다."));
			updated.update(request.getDpName(), request.getDpDetail(), empEntity);
			Department saveData = departmentRepository.save(updated);
			ResDepartmentUpdateDTO response = ResDepartmentUpdateDTO.builder()
					.dpCode(saveData.getDpCode())
					.dpName(saveData.getDpName())
					.dpNum(saveData.getDpNum())
					.dpDetail(saveData.getDpDetail())
					.dpManager(Optional.ofNullable(search.getDpManager())
							.map(manager -> manager.getName())
							.orElse(null)))
					.build();
			return response;
		}
	
	// 부서 수정 페이지 부서원 목록 조회
	@Transactional(readOnly = true)
	public List<ResUpdateMemberListDTO> updateList (Long id) {
		List<Employee> employee = employeeRepository.findByDpNum(id).orElse(null); // EmployeeRepository 부서 코드 FK에 해당하는 부서원만 찾기
		List<ResUpdateMemberListDTO> response = employee.stream()
				.map(emp -> ResUpdateMemberListDTO.builder()
						.empNum(emp.getEmpId())
						.name(emp.getName())
						.position(emp.getPosition())
						.email(emp.getEmail())
						.hire_date(emp.getHireDate())
						.build())
				.collect(Collectors.toList());			
		return response;
				
	}
	
	// 부서 관리(삭제)
	@Transactional
	public void departmentDelete(Long id) {
		Department department = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("삭제할 부서가 없습니다."));
		
		departmentRepository.delete(department);		
		
	}

}
