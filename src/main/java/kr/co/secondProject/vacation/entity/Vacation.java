package kr.co.secondProject.vacation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "vacation")
public class Vacation {
	
	private String annualCode;
	
//	@JoinColumn(name="id")
    private Long employeeId;        // 직원 ID
	
//	@JoinColumn(name="id")
	private String proxyEmpId;		// 대리 신청자 ID
	
//	@JoinColumn(name="dp_num")
	private String dpNum;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JoinColumn(name="Vacation_code")
	private Long vacationCode;		// 휴가 코드
	
	private LocalDateTime startTime;// 휴가 시작일
	
	private LocalDateTime endTime;	// 휴가 끝일
	
	private Double remaining;		// 잔여 휴가
	
	private Boolean approval;		// 승인 상태
	
	
	
	private String kind;			// 휴가 사유(교육,병가, 경조사, 기타)
	
	private String reason;			// 기타 사유


	
}
