package kr.co.secondProject.department.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.co.secondProject.department.dto.ReqDepartmentCreateDTO;
import kr.co.secondProject.login.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dpNum; // 부서 번호 PK
	
	@Column
	private String dpName; // 부서 이름
	
	@Column
	private String dpDetail; // 부서 설명
	
	@Column
	private String dpCode; // 부서 코드
	
	@OneToOne
	@JoinColumn(name="dp_manager")
	private Employee dpManager; // Employee 테이블과 조인 부서장 번호
	
	@Column
	private LocalDateTime dpCreatedDate;
	
	public void update(String dpName, String dpDetail, Employee manager) {
	    this.dpName = dpName;
	    this.dpDetail = dpDetail;
	    this.dpManager = manager;
	}
	
	public static Department of(ReqDepartmentCreateDTO request, Employee manager) {
		Department dpEntity = Department.builder()
				.dpCode(request.getDpCode())
				.dpName(request.getDpName())
				.dpDetail(request.getDpDetail())
				.dpCreatedDate(LocalDateTime.now())
				.dpManager(manager)
				.build();
		
		return dpEntity;
	}
	
}
