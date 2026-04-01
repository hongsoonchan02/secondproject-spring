package kr.co.secondProject.employee.dto;

import java.time.LocalDateTime;

import kr.co.secondProject.users.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeCreateRequestDto {
	String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

	Employee employee = Employee.create(
	        requestDto.getEmpId(),
	        requestDto.getName(),
	        requestDto.getEmail(),
	        encodedPassword,
	        requestDto.getHireDate(),
	        requestDto.getStatus(),
	        requestDto.getRole(),
	        requestDto.getPosition(),
	        requestDto.getDpNum()
	);
}