package kr.co.secondProject.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "대시보드 응답 DTO")
public class DashboardResDTO {

    @Schema(description = "직원 이름", example = "홍길동")
    private String name;

    @Schema(description = "직책", example = "대리")
    private String position;

    @Schema(description = "권한", example = "EMPLOYEE")
    private String role;

    @Schema(description = "오늘 출근 여부")
    private boolean checkedInToday;

    @Schema(description = "오늘 출근 시각 (출근 전이면 null)")
    private LocalDateTime todayStartTime;

    @Schema(description = "이번달 출근 횟수", example = "15")
    private int monthlyWorkDays;

    @Schema(description = "출근 기록 목록")
    private List<AttendanceRecordDTO> attendanceList;
}