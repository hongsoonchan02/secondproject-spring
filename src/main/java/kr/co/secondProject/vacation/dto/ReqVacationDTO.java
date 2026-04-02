package kr.co.secondProject.vacation.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqVacationDTO {
	
	@Schema(description = "연차 코드", example = "de-1001")
	@JoinColumn(name="annual_code")
	private String annualCode;		// 연차 코드
	
	@Schema(description = "휴가 코드", example = "1001")
	@JoinColumn(name="vacation_code")
	private Long vacationCode;		// 휴가 코드
	
	@Schema(description = "휴가 대리신청자", example = "홍길동")
	private Long targetEmployeeId;
	
	@Schema(description = "휴가 시작일", example = "2026.03.30")
	private LocalDateTime startTime;// 휴가 시작일
	
	@Schema(description = "휴가 끝일", example = "2026.03.30")
	private LocalDateTime endTime;	// 휴가 끝일
	
	@Schema(description = "휴가 사용 일수", example = "1.5")
	private Double usedDays;		// 사용 일수
	
	@Schema(description = "잔여 휴가", example = "1")
	private Double remaining;			// 잔여 휴가
	
	@Schema(description = "승인 상태", example = "null")
	private Boolean approval;		// 승인 상태
	
	@Schema(description = "휴가 사유", example = "교육")
	private String kind;			// 휴가 종류
	
	@Schema(description = "기타 사유", example = "개인 사정")
	private String reason;			// 기타 사유
	
	@Schema(description = "대리 신청자", example = "de-1001")
	private String proxyEmpId;		// 대리 신청자 ID



	
}
