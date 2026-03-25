package kr.co.secondProject.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqDepartmentUpdateDTO {

	private String dpName;
	private String dpDetail;
	private String dpManagerName;
	private String dpManagerEmpNum;

}
