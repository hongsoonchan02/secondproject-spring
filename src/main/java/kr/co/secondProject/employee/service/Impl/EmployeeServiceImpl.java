package kr.co.secondProject.employee.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;
import kr.co.secondProject.employee.entity.Employee;
import kr.co.secondProject.employee.repository.EmployeeManageRepository;
import kr.co.secondProject.employee.service2.EmployeeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeManageRepository employeeRepository;

    @Override
    public List<EmployeeResponseDto> getEmployees(String name) {
        List<Employee> employees;

        if (name != null && !name.isBlank()) {
            employees = employeeRepository.findByNameContaining(name);
        } else {
            employees = employeeRepository.findAll();
        }

        return employees.stream()
                .map(EmployeeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원이 없습니다. id=" + id));

        return EmployeeResponseDto.from(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto requestDto) {
        if (employeeRepository.existsByEmpNum(requestDto.getEmpNum())) {
            throw new IllegalArgumentException("이미 존재하는 사원번호입니다.");
        }

        if (employeeRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Employee employee = Employee.builder()
                .empNum(requestDto.getEmpNum())
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .hireDate(requestDto.getHireDate())
                .role(requestDto.getRole())
                .position(requestDto.getPosition())
                .dpNum(requestDto.getDpNum())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeResponseDto.from(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateRequestDto requestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원이 없습니다. id=" + id));

        employee.update(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getHireDate(),
                requestDto.getRole(),
                requestDto.getPosition(),
                requestDto.getDpNum()
        );

        return EmployeeResponseDto.from(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원이 없습니다. id=" + id));

        employeeRepository.delete(employee);
    }
}