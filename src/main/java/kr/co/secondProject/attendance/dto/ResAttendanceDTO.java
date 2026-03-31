package kr.co.secondProject.attendance.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.login.entity.Attendance;
import lombok.*;


/**
 * 근태 이력 조회 응답 DTO
 * 날짜 / 출근시각 / 퇴근시각 / 근무시간 / 상태
 */
@Schema(description = "근태 이력 조회 응답 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResAttendanceDTO {

    @Schema(description = "근태 코드", example = "1001")
    private Long attendanceId;

    @Schema(description = "직원 ID", example = "1")
    private Long employeeId;

    @Schema(description = "직원 이름", example = "홍길동")
    private String employeeName;

    @Schema(description = "근태 날짜", example = "2026년 03월 27일 금요일")
    private LocalDateTime date;

    @Schema(description = "출근 시간", example = "T09:00 AM")
    private LocalDateTime startTime;

    @Schema(description = "퇴근 시간", example = "T18:00 PM")
    private LocalDateTime endTime;

    @Schema(description = "근무 시간", example = "9시간 0분")
    private String allTime;

    @Schema(
        description = "근태 상태",
        example = "정상",
        allowableValues = {"정상", "지각", "휴가", "조퇴", "연장 근무"}
    )
    private String state;
    
    
    // Entity → ResAttendanceDTO 변환
    public static ResAttendanceDTO from(Attendance attendance) {
        return ResAttendanceDTO.builder()
        					   .attendanceId(attendance.getAttendanceId())
        					   .date(attendance.getDate())
        					   .startTime(attendance.getStartTime())
        					   .endTime(attendance.getEndTime())
        					   .allTime(attendance.getAllTime())
        					   .state(attendance.getState())
        					   .employeeId(attendance.getEmployee() != null 
                               		? attendance.getEmployee().getId() : null)
        					   .employeeName(attendance.getEmployee() != null 
                               		? attendance.getEmployee().getName() : null)
        					   .build();
    }
}