package kr.co.secondProject.attendance.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 출근/퇴근 등록 및 수정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAttendanceDTO {

    private Long employeeId;        // 직원 ID

    private LocalDateTime date;     // 날짜

    private LocalDateTime startTime;// 출근 시간

    private LocalDateTime endTime;  // 퇴근 시간

    private String state;           // 근태 상태 (정상, 지각, 결근 등)
}