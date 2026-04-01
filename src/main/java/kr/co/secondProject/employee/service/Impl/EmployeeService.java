package kr.co.secondProject.employee.service.Impl;

import java.util.List;

import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;



public interface EmployeeService {

    List<EmployeeResponseDto> getEmployees(String name, String status);

    EmployeeResponseDto createEmployee(EmployeeCreateRequestDto requestDto);

    EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateRequestDto requestDto);

    void resignEmployee(Long id);
}