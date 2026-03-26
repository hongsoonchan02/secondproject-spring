package kr.co.secondProject.department.dto;

import kr.co.secondProject.department.entity.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResDepartmentCreateDTO {
	
	private String dpCode;
	private String dpName;
	private String dpDetail;
	private String dpManagerName;
	
	
	public static ResDepartmentCreateDTO toDto (Department department) {
		ResDepartmentCreateDTO response = ResDepartmentCreateDTO.builder()
				.dpCode(department.getDpCode())
				.dpDetail(department.getDpDetail())
				.dpName(department.getDpDetail())
				.dpManagerName(department.getDpManager().getName())
				.build();
		return response;
	}
	

}
