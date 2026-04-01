package kr.co.secondProject.employee.service.Impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;
import kr.co.secondProject.employee.entity.Employee;
import kr.co.secondProject.employee.repository.EmployeeManageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeManageRepository employeeManageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeResponseDto> getEmployees(String name, String status) {
        List<Employee> employees;

        boolean hasName = name != null && !name.isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        if (hasName && hasStatus) {
            employees = employeeManageRepository.findByNameContainingAndStatus(name, status);
        } else if (hasName) {
            employees = employeeManageRepository.findByNameContaining(name);
        } else if (hasStatus) {
            employees = employeeManageRepository.findByStatus(status);
        } else {
            employees = employeeManageRepository.findAll();
        }

        return employees.stream()
                .map(EmployeeResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto requestDto) {
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

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateRequestDto requestDto) {
        Employee employee = employeeManageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원이 없습니다. id=" + id));

        employee.updateInfo(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getStatus(),
                requestDto.getRole(),
                requestDto.getPosition(),
                requestDto.getDpNum()
        );

        return EmployeeResponseDto.from(employee);
    }

    @Override
    @Transactional
    public void resignEmployee(Long id) {
        Employee employee = employeeManageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원이 없습니다. id=" + id));

        employee.resign();
    }
}