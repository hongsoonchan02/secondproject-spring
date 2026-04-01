package kr.co.secondProject.employee.dto;

import kr.co.secondProject.employee.entity.Employee;

public class EmployeeResponseDto {
	
	createEmployee(EmployeeCreateRequestDto requestDto) {
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

    Employee savedEmployee = employeeManageRepository.save(employee);
    return EmployeeResponseDto.from(savedEmployee);
}