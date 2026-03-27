package kr.co.secondProject.vacation.dto;

import java.time.LocalDateTime;

import jakarta.persistence.JoinColumn;
import kr.co.secondProject.vacation.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqVacationDTO {
	
	@JoinColumn(name="annual_code")
	private String annualCode;		// 연차 코드
	
	@JoinColumn(name="vacation_code")
	private Long vacationCode;		// 휴가 코드
	
	private LocalDateTime startTime;// 휴가 시작일
	
	private LocalDateTime endTime;	// 휴가 끝일
	
	private int remaining;			// 잔여 휴가
	
	private boolean approval;		// 승인 상태
	
	private String kind;			// 휴가 종류
}
