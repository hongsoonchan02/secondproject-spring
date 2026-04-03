package kr.co.secondProject.dashboard.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.login.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출근 기록 단건 DTO")
public class AttendanceRecordDTO {

    @Schema(description = "날짜", example = "2026-03-25T00:00:00")
    private LocalDateTime date;

    @Schema(description = "출근시각", example = "2026-03-25T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "퇴근시각", example = "2026-03-25T18:00:00")
    private LocalDateTime endTime;

    @Schema(description = "근무시간", example = "9시간 0분")
    private Long allTime;

    @Schema(description = "근태상태", example = "정상")
    private String state;

    public static AttendanceRecordDTO from(Attendance a){
        return AttendanceRecordDTO.builder()
                .date(a.getDate())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .allTime(a.getAllTime())
                .state(a.getState())
                .build();

    }
}
