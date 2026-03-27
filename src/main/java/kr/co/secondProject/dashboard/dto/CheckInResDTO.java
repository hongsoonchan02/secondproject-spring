package kr.co.secondProject.dashboard.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출근 처리 응답 DTO")
public class CheckInResDTO {

    @Schema(description = "출근 시각", example = "2026-03-25T09:02:00")
    private LocalDateTime startTime;

    @Schema(description = "출근 날짜", example = "2026-03-25")
    private String date;

    @Schema(description = "근태상태", example = "정상")
    private String state;


}
