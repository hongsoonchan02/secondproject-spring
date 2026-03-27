package kr.co.secondProject.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 출근/퇴근 등록 및 수정 요청 DTO
 */
@Schema(description = "출근/퇴근 등록 및 수정 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReqAttendanceDTO {

    @Schema(description = "직원 ID", example = "1")
    private Long employeeId;

    @Schema(description = "근태 날짜", example = "2026년 03월 27일 금요일")
    private LocalDateTime date;

    @Schema(description = "출근 시간", example = "T09:00 AM")
    private LocalDateTime startTime;

    @Schema(description = "퇴근 시간", example = "T18:00 PM")
    private LocalDateTime endTime;

    @Schema(
        description = "근태 상태",
        example = "정상",
        allowableValues = {"정상", "지각", "휴가", "조퇴", "연장 근무"}
    )
    private String state;
}