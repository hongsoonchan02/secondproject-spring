package kr.co.secondProject.employee.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeUpdateRequestDto {

    private String name;
    private String email;
    private String status;
    private String role;
    private String position;
    private String dpNum;
}