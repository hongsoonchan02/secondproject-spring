package kr.co.secondProject.employee.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeUpdateRequestDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "입사일은 필수입니다.")
    private LocalDate hireDate;

    @NotBlank(message = "역할은 필수입니다.")
    private String role;

    @NotBlank(message = "직급은 필수입니다.")
    private String position;

    @NotNull(message = "부서번호는 필수입니다.")
    private Integer dpNum;
}