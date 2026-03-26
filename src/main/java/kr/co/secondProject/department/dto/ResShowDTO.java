package kr.co.secondProject.department.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResShowDTO {
	
	private List<ResDepartmentListDTO> dpList;
	private ResHeaderDTO header;
	

}
