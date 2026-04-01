package kr.co.secondProject.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.secondProject.employee.dto.EmployeeCreateRequestDto;
import kr.co.secondProject.employee.dto.EmployeeResponseDto;
import kr.co.secondProject.employee.dto.EmployeeUpdateRequestDto;
import kr.co.secondProject.employee.service.Impl.EmployeeService;
import lombok.RequiredArgsConstructor;


@Tag(name = "Employee", description = "직원 관리 API")
@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
        summary = "직원 조회",
        description = "전체 직원 조회, 재직자 조회, 이름 검색을 하나의 API로 처리합니다."
    )
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status) {

        List<EmployeeResponseDto> result = employeeService.getEmployees(name, status);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "직원 등록", description = "직원을 등록합니다.")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @RequestBody EmployeeCreateRequestDto requestDto) {

        EmployeeResponseDto result = employeeService.createEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "직원 수정", description = "직원 정보를 부분 수정합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeUpdateRequestDto requestDto) {

        EmployeeResponseDto result = employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "직원 퇴사 처리", description = "직원의 상태를 RESIGNED로 변경하고 퇴사일을 저장합니다.")
    @PatchMapping("/{id}/resign")
    public ResponseEntity<Void> resignEmployee(@PathVariable Long id) {
        employeeService.resignEmployee(id);
        return ResponseEntity.noContent().build();
    }
}