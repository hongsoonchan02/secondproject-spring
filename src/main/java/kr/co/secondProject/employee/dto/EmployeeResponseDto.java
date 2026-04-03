package kr.co.secondProject.employee.dto;

import java.time.LocalDate;

import kr.co.secondProject.employee.entity.Employee;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDto {

    private Long id;
    private String empNum;
    private String name;
    private String email;
    private LocalDate hireDate;
    private String role;
    private String position;
    private Integer dpNum;

    public static EmployeeResponseDto from(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .empNum(employee.getEmpNum())
                .name(employee.getName())
                .email(employee.getEmail())
                .hireDate(employee.getHireDate())
                .role(employee.getRole())
                .position(employee.getPosition())
                .dpNum(employee.getDpNum())
                .build();
    }
}