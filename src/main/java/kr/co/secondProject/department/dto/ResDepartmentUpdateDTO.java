package kr.co.secondProject.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kr.co.secondProject.login.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter; 
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDepartmentUpdateDTO {

	@Schema(description = "부서 이름", example = "시스템 엔지니어링")
	private String dpName;
	
	@Schema(description = "부서 설명", example = "주요 인프라")
	private String dpDetail;
	
	@Schema(description = "부서 관리자 성명", example = "아드리안 머서")
	private String dpManagerName;
	
	@Schema(description = "부서 관리자 직책", example = "매니저")
	private String dpManagerPosition;
	
}
