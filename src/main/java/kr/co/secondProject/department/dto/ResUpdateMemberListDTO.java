package kr.co.secondProject.department.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUpdateMemberListDTO {
	
	private String empNum;
	private String name;
	private String position;
	private String email;
	
	private LocalDateTime hire_date;

}
