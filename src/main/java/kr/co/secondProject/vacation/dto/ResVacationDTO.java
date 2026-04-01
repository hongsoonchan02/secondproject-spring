package  kr.co.secondProject.vacation.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.secondProject.vacation.entity.Annual;
import kr.co.secondProject.vacation.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "휴가 관련 응답 DTO")
public class ResVacationDTO {

    // ── 휴가 기본 정보 ──────────────────────────
	@Schema(description = "휴가 코드", example = "1001")
    private Long vacationCode;
	@Schema(description = "휴가 시작일", example = "2026.03.30")
    private LocalDateTime startTime;
	@Schema(description = "휴가 끝일", example = "2026.03.31")
    private LocalDateTime endTime;


	@Schema(description = "휴가 일수", example = "1일")
    private Double remaining;

    /**
     * 승인 상태
     * null  → "대기"
     * true  → "승인됨"
     * false → "반려됨"
     */
	@Schema(description = "승인 상태", example = "null")
    private Boolean approval;

    /**
     * 화면 표시용 승인 상태 문자열
     * "대기" / "승인됨" / "반려됨"
     */
	@Schema(description = "휴가 코드", example = "요청")
    private String approvalLabel;

    /**
     * 휴가 종류
     * "병가", "교육", "경조사", "기타"
     */
	@Schema(description = "휴가 사유", example = "교육")
    private String kind;

    /**
     * 기타 상세사유 (kind = "기타" 일 때 필수)
     */
	@Schema(description = "기타 상세사유", example = "개인 사정")
    private String reason;
	
	@Schema(description = "직원 코드", example = "1001")
	private Long employeeId;
	
	@Schema(description = "대리 신청자 코드", example = "1001")
	private String proxyEmpId;

	@Schema(description = "연차 코드", example = "15")
    private String annualCode;
	@Schema(description = "부서 코드", example = "ad-1001")
    private String dpNum;
	
    // ── 휴가 관리 화면 – 잔여/사용 통계 ─────────

	@Schema(description = "잔여 연차", example = "15")
    private Double remainingAnnual;

	@Schema(description = "총 사용 연차", example = "3")
    private Double usedAnnual;

	@Schema(description = "승인 대기 건수", example = "2")
    private Long pendingCount;

	@Schema(description = "연차 유효 기간", example = "2026.04.01")
    private String annualExpiry;


	
	/**
     * 엔티티 → DTO 변환 (공통)
     */
    public static ResVacationDTO from(Vacation vacation) {
        String approvalLabel;
        if (vacation.getApproval() == null)      approvalLabel = "대기";
        else if (vacation.getApproval())         approvalLabel = "승인됨";
        else                              approvalLabel = "반려됨";

        return ResVacationDTO.builder()
                .vacationCode(vacation.getVacationCode())
                .startTime(vacation.getStartTime())
                .endTime(vacation.getEndTime())
                .remaining(vacation.getRemaining())
                .approval(vacation.getApproval())
                .approvalLabel(approvalLabel)
                .kind(vacation.getKind())
                .reason(vacation.getReason())
                .dpNum(vacation.getDpNum())
                .employeeId(vacation.getEmployeeId())
                .proxyEmpId(vacation.getProxyEmpId())
                .build();
    }
}
