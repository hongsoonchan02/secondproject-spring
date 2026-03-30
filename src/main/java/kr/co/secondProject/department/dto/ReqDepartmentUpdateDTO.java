package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqDepartmentUpdateDTO {

	@Schema(description = "부서명", example = "시스템 엔지니어링")
	private String dpName;
	
	@Schema(description = "부서 설명", example = "주요 인프라")
	private String dpDetail;
	
	@Schema(description = "부서 관리자 성명", example = "엘레나 룬드")
	private String dpManagerName;
	
	@Schema(description = "부서 관리자 사변", example = "#44572")
	private String dpManagerEmpNum;

}
