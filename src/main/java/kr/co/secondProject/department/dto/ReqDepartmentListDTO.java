package kr.co.secondProject.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqDepartmentListDTO {
	
	private String dpCode; 
	private String dpName;
	private Long dpManager;
	private String dpDetail;

}
