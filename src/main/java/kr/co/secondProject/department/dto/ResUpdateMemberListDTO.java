package kr.co.secondProject.department.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUpdateMemberListDTO {
	
	@Schema(description = "사번", example = "EMP-2023-01")
	private String empId;
	
	@Schema(description = "성명", example = "김철수")
	private String name;
	
	@Schema(description = "직책", example = "본부장")
	private String position;
	
	@Schema(description = "이메일", example = "test@test.com")
	private String email;
	
	@Schema(description = "입사일", example = "2023.01.15")
	private LocalDateTime hireDate;

}
