package kr.co.secondProject.employee.service2;

import java.util.List;

import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;

public interface EmployeeService {

    List<EmployeeResponseDto> getEmployees(String name);

    EmployeeResponseDto getEmployee(Long id);

    EmployeeResponseDto createEmployee(EmployeeCreateRequestDto requestDto);

    EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateRequestDto requestDto);

    void deleteEmployee(Long id);
}