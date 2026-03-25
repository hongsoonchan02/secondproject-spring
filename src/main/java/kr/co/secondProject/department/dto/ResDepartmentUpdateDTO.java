package kr.co.secondProject.department.dto;

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

	private String dpCode; // 부서 코드
	private String dpName; // 부서 이름
	private String dpDetail; // 부서 설명
	private String dpManager; // 부서 관리자
	private String dpManagerDetail; // 부서 관리자 설명
	private int dpMember; // 부서 인원
	private String dpManagerNum; // 부서 관리자 사번
}
