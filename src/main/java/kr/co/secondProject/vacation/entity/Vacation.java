package kr.co.secondProject.vacation.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vacation")
public class Vacation {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
    private Long employeeId;        // 직원 ID
	
	@JoinColumn(name="annual_code")
	private String annualCode;		// 연차 코드
	
	private LocalDateTime startTime;// 휴가 시작일
	
	private LocalDateTime endTime;	// 휴가 끝일
	
	private int remaining;			// 잔여 휴가
	
	private boolean approval;		// 승인 상태
	
	private String kind;			// 휴가 종류
	
	private int joinYears;			// 입사 연수
	
	private int annualNum;			// 연처 갯수
	
}
