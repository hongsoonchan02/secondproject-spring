package kr.co.secondProject.attendance.dto;

import java.time.LocalDateTime;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 근태 이력 조회 응답 DTO
 * 날짜 / 출근시각 / 퇴근시각 / 근무시간 / 상태
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResAttendanceDTO {

	@JoinColumn(name="attendance_id")
    private Long attendanceId;      // 근태 코드

    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
    private Long employeeId;        // 직원 ID

    private String employeeName;    // 직원 이름 (표시용)

    private LocalDateTime date;     // 날짜

    private LocalDateTime startTime;// 출근 시각

    private LocalDateTime endTime;  // 퇴근 시각

    private String allTime;         // 근무 시간 (예: "9시간 18분")

    private String state;           // 근태 상태 (정상 / 지각 / 결근)
}