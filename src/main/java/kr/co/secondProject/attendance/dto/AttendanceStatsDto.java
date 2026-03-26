package kr.co.secondProject.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 근태 통계 카드용 DTO
 * Attendance.jsx 상단 stat-card 4개에 대응:
 * - 이번 달 출근 일수
 * - 지각 횟수
 * - 총 결근 일수
 * - 근태 점수
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatsDto {

    private int workDays;           // 이번 달 출근 일수

    private int lateDays;           // 지각 횟수

    private int absentDays;         // 총 결근 일수

    private double attendanceScore; // 근태 점수 (%)
}