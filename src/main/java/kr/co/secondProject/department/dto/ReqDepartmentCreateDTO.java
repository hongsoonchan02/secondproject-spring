package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqDepartmentCreateDTO {
	
	@Schema(description = "부서 코드", example = "EXP-564")
	private String dpCode;
	
	@Schema(description = "부서명", example = "시스템 엔지니어링")
	private String dpName;
	
	@Schema(description = "부서 관리자 사번", example = "#15461")
	private String dpManagerEmpNum;
	
	@Schema(description = "부서 설명", example = "주요 인프라")
	private String dpDetail;
	
}
