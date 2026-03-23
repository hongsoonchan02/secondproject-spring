package kr.co.secondProject.department.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dpNum; // 부서 번호 PK
	
	private String dpName; // 부서 이름
	
	private String dpDetail; // 부서 설명
	
	private String dpCode; // 부서 코드
	
	@OneToOne
	@JoinColumn(name="id")
	private Employee dpManagerId; // Employee 테이블과 조인
}
