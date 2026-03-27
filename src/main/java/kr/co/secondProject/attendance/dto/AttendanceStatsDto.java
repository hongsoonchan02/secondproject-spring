package kr.co.secondProject.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 근태 통계 카드용 DTO
 * Attendance.jsx 상단 stat-card 4개에 대응:
 * - 이번 달 출근 일수
 * - 지각 횟수
 * - 총 결근 일수
 * - 근태 점수
 */
@Schema(description = "근태 통계 카드 DTO")
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatsDto {

    @Schema(description = "이번 달 출근 일수", example = "20")
    private int workDays;

    @Schema(description = "지각 횟수", example = "3")
    private int lateDays;

    @Schema(description = "총 결근 일수", example = "1")
    private int absentDays;

    @Schema(description = "근태 점수 (%)", example = "92.5", minimum = "0", maximum = "100")
    private double attendanceScore;
}