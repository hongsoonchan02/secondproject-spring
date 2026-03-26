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
	
//	private ResDepartmentCreateDTO(Department department) {
//		this.dpCode = department.getDpCode();
//		this.dpName = department.getDpName();
//		this.dpDetail = department.getDpDetail();
//		this.dpManagerName = department.getDpManager().getName();
//	}
	
	
	public static ResDepartmentCreateDTO from (Department department) {
		ResDepartmentCreateDTO response = ResDepartmentCreateDTO.builder()
				.dpCode(department.getDpCode())
				.dpDetail(department.getDpDetail())
				.dpName(department.getDpName())
				.dpManagerName(department.getDpManager().getName())
				.build();
		return response;
	}


	

}
