package kr.co.secondProject.department.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.department.entity.Department;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDepartmentListDTO {
	
	private static EmployeeRepository employeeRepository;
	
	@Schema(description = "부서 번호", example = "1")
	private Long dpNum;

	@Schema(description = "부서 코드", example = "EXP-564")
	private String dpCode; 
	
	@Schema(description = "부서명", example = "시스템 엔지니어링")
	private String dpName;

	@Schema(description = "부서 관리자 성명", example = "아드리안 머서")
	private String dpManagerName; 
	
	@Schema(description = "부서원 수", example = "154")
	private int dpMember; 
	
	@Schema(description = "평균 근속", example = "5.7")
	private float averegeYear;
	
	public static ResDepartmentListDTO from(Department department) {
		
	return ResDepartmentListDTO.builder()
		.dpNum(department.getDpNum())
		.dpCode(department.getDpCode())
		.dpName(department.getDpName())
		.dpManagerName(Optional.ofNullable(department.getDpManager())
				.map(manager -> manager.getName())
				.orElse(null))
		.dpMember(employeeRepository.countByDpNum(department.getDpNum()))
		.averegeYear(averegeYear(department))
		.build();
	}
	
	// 평균 근속을 구하기 위한 매서드
	public static float averegeYear(Department department) {
		List<Employee> employeeEntity = employeeRepository.findByDpNum(department.getDpNum());
		int currentYear = LocalDate.now().getYear();
		int totalYear = employeeEntity.stream()
				.mapToInt(emp -> currentYear - emp.getHireDate().getYear())
				.sum();
		float averegeYear = totalYear / employeeRepository.count();
		return averegeYear;
	}
}
