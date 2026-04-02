package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.department.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDepartmentUpdateDTO {
	
	@Schema(description = "부서 번호", example = "1")
	private Long dpNum;

	@Schema(description = "부서 이름", example = "시스템 엔지니어링")
	private String dpName;
	
	@Schema(description = "부서 설명", example = "주요 인프라")
	private String dpDetail;
	
	@Schema(description = "부서 관리자 성명", example = "아드리안 머서")
	private String dpManagerName;
	
	@Schema(description = "부서 관리자 직책", example = "매니저")
	private String dpManagerPosition;
	
	
	public ResDepartmentUpdateDTO toDto (Department dp) {
		ResDepartmentUpdateDTO response = ResDepartmentUpdateDTO.builder()
				.dpNum(dp.getDpNum())
				.dpName(dp.getDpName())
				.dpDetail(dp.getDpDetail())
				.dpManagerName(dp.getDpManager().getName())
				.dpManagerPosition(dp.getDpManager().getPosition())
				.build();
		
		return response;		
	}
}
