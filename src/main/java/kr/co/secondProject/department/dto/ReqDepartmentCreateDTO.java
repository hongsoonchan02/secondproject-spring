package kr.co.secondProject.department.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqDepartmentCreateDTO {
	
	private String dpCode;
	private String dpName;
	private String dpManagerEmpNum;
	private String dpDetail;
	
}
