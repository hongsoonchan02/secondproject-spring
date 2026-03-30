package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.login.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDepartmentListDTO {

	@Schema(description = "부서 코드", example = "EXP-564")
	private String dpCode; 
	
	@Schema(description = "부서명", example = "시스템 엔지니어링")
	private String dpName;

	@Schema(description = "부서 관리자 성명", example = "아드리안 머서")
	private String dpManagerName; 
	
	@Schema(description = "부서원 수", example = "154")
	private int dpMember; 
	
	@Schema(description = "평균 근속", example = "5.7")
	private float averegeYear;
}
