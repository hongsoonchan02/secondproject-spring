package kr.co.secondProject.department.service.impl;

import java.time.LocalDate;
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
	
	// 부서 추가
	@Transactional
	@Override
	public ResDepartmentCreateDTO departmentCreate(ReqDepartmentCreateDTO dto) {
		//부서 생성 기능 부분                    // EmployeeRepository에서 구현해야함                         // 찾는 데이터가 없을때(사번으로 직원employee 엔티티)
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
	
	// 최근 생성 부서 목록
	public List<ResCurrentDpListDTO> currentDpList() {
		List<Department> dpList = departmentRepository.findTop5ByorderByIdDesc();
		
		List<ResCurrentDpListDTO> responseList = dpList.stream()
				.map(department -> ResCurrentDpListDTO.builder()
						.dpCode(department.getDpCode())
						.dpName(department.getDpName())
						.build())
				.collect(Collectors.toList());
		
		return responseList;
	}
	
	// 부서 리스트 조회 및 검색
		@Transactional(readOnly = true)
		public List<ResDepartmentListDTO> departmentList(String keyword) {
			
			List<ResDepartmentListDTO> response;
			
			// 부서 리스트 조회	(검색)	
			if (keyword.equals("")) {
				List<Department> dpList = departmentRepository.findAll();
				response = dpList.stream()
						.map(department -> ResDepartmentListDTO.builder()
								.dpCode(department.getDpCode())
								.dpName(department.getDpName())
								.dpManagerName(Optional.ofNullable(department.getDpManager())
										.map(manager -> manager.getName())
										.orElse(null))
								.dpMember(employeeRepository.countByDpNum(department.getDpNum())) // 부서에 맞는 인원 로직이 담긴 명령어 아직 구현 안함.
								.averegeYear(averegeYear(department))
								.build())
						.collect(Collectors.toList());
				
			} else {
				List<Department> dpList = departmentRepository.findByDpNameContaining(keyword);
				response = dpList.stream()
						.map(department -> ResDepartmentListDTO.builder()
								.dpCode(department.getDpCode())
								.dpName(department.getDpName())
								.dpManagerName(Optional.ofNullable(department.getDpManager())
										.map(manager -> manager.getName())
										.orElse(null))
								.dpMember(employeeRepository.countByDpNum(department.getDpNum()))
								.averegeYear(averegeYear(department))
								.build())
				
						.collect(Collectors.toList());
			}
	
			return response;
		}
	
	// 부서 관리(수정)
	@Transactional
	public ResDepartmentUpdateDTO departmentUpdate(Long id, ReqDepartmentUpdateDTO request) {
		// 부서 수정 기능                                                            // 수정하고자 하는 부서를 못 찾을때
		Department updated = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 부서가 없습니다."));
			                                      // EmployeeRepository에 구현해야함                                // 잘못된 인자가 들어왔을때(부서 코드)
		Employee empEntity = employeeRepository.findByEmpId(request.getDpManagerEmpNum()).orElseThrow(() -> new IllegalArgumentException("잘못된 부서 코드입니다."));
		updated.update(request.getDpName(), request.getDpDetail(), empEntity);
		ResDepartmentUpdateDTO responseUpdate = ResDepartmentUpdateDTO.builder()
				.dpManagerName(Optional.ofNullable(updated.getDpManager())
						.map(manager -> manager.getName())
						.orElse(null))
				.dpName(updated.getDpName())
				.dpDetail(updated.getDpDetail()) 
				.dpManagerPosition(Optional.ofNullable(updated.getDpManager())
						.map(manager -> manager.getPosition())
						.orElse(null))
				.build();
		
		return responseUpdate;
		
		}
	
	public List<ResUpdateMemberListDTO> updateList(Long id) {
		
		// 해당 부서 부서원 목록 조회
		List<Employee> employee = employeeRepository.findByDpNum(id); // EmployeeRepository 부서 코드 FK에 해당하는 부서원만 찾기
		List<ResUpdateMemberListDTO> responseMember = employee.stream()
				.map(emp -> ResUpdateMemberListDTO.builder()
						.empId(emp.getEmpId())
						.name(emp.getName())
						.position(emp.getPosition())
						.email(emp.getEmail())
						.hireDate(emp.getHireDate())
						.build())
				.collect(Collectors.toList());
		
		return responseMember;
	}
	
	// 부서 관리(삭제)
	@Transactional
	public void departmentDelete(Long id) {
		Department department = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("삭제할 부서가 없습니다."));
		
		departmentRepository.delete(department);		
		
	}
	
	// 평균 근속을 구하기 위한 매서드
	public float averegeYear(Department department) {
		
		List<Employee> employeeEntity = employeeRepository.findByDpNum(department.getDpNum());
		
		int currentYear = LocalDate.now().getYear();
		
		int totalYear = employeeEntity.stream()
				.mapToInt(emp -> currentYear - emp.getHireDate().getYear())
				.sum();
		
		float averegeYear = totalYear / employeeRepository.count();
		
		return averegeYear;
	}

}
