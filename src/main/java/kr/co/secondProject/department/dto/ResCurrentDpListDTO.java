package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResCurrentDpListDTO {
	
	@Schema(description = "부서명", example = "성장 및 전략")
	private String dpName;
	
	@Schema(description = "부서 코드", example = "MKT-485")
	private String dpCode;

}
