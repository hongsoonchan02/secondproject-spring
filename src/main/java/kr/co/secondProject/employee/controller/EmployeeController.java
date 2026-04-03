package kr.co.secondProject.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;
import kr.co.secondProject.employee.service2.EmployeeService;
import kr.co.secondProject.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getEmployees(
            @RequestParam(required = false) String name) {

        List<EmployeeResponseDto> employees = employeeService.getEmployees(name);
        return ResponseEntity.ok(ApiResponse.ok("직원 조회 성공", employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployee(@PathVariable Long id) {
        EmployeeResponseDto employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(ApiResponse.ok("직원 상세 조회 성공", employee));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> createEmployee(
            @Valid @RequestBody EmployeeCreateRequestDto requestDto) {

        EmployeeResponseDto employee = employeeService.createEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("직원 등록 성공", employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequestDto requestDto) {

        EmployeeResponseDto employee = employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(ApiResponse.ok("직원 수정 성공", employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.ok("직원 삭제 성공", null));
    }
}