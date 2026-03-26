package kr.co.secondProject.department.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResCurrentDpListDTO {
	
	private String dpName;
	private String dpCode;

}
