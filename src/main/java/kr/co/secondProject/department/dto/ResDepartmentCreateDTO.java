package kr.co.secondProject.department.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.department.entity.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResDepartmentCreateDTO {
	
	@Schema(description = "부서 코드", example = "EXP-564")
	private String dpCode;
	
	@Schema(description = "부서명", example = "성장 및 전략")
	private String dpName;
	
	@Schema(description = "부서 설명", example = "수익 운영")
	private String dpDetail;
	
	@Schema(description = "부서 관리자 이름", example = "엘레나 룬드")
	private String dpManagerName;
	
	@Schema(description = "부서 생성일", example = "2026-04-02")
	private LocalDateTime dpCreatedDate; 
	

	
	
	
	public static ResDepartmentCreateDTO from (Department department) {
		ResDepartmentCreateDTO response = ResDepartmentCreateDTO.builder()
				.dpCode(department.getDpCode())
				.dpDetail(department.getDpDetail())
				.dpName(department.getDpName())
				.dpManagerName(department.getDpManager().getName())
				.dpCreatedDate(department.getDpCreatedDate())
				.build();
		return response;
	}


	

}
