package kr.co.secondProject.department.dto;

import java.time.LocalDateTime;
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
public class ResCurrentDpListDTO {
	
	@Schema(description = "부서 번호", example = "1")
	private Long dpNum;
	
	@Schema(description = "부서명", example = "성장 및 전략")
	private String dpName;
	
	@Schema(description = "부서 코드", example = "MKT-485")
	private String dpCode;
	
	@Schema(description = "부서 생성일", example = "2026-04-02")
	private LocalDateTime dpCreatedDate;
	
	public static ResCurrentDpListDTO from(Department department) {
		
		return ResCurrentDpListDTO.builder()
				.dpCode(department.getDpCode())
				.dpName(department.getDpName())
				.dpCreatedDate(department.getDpCreatedDate())
				.build();
	}

}
