package kr.co.secondProject.attendance.sevice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import kr.co.secondProject.attendance.dto.AttendanceStatsDto;
import kr.co.secondProject.attendance.dto.ReqAttendanceDTO;
import kr.co.secondProject.attendance.dto.ResAttendanceDTO;



public interface AttendanceService {

	
	/**
     * 특정 직원의 이번 달 근태 통계 조회 (비동기)
     * @param employeeId 직원 ID
     */
    AttendanceStatsDto getAttendanceStats(Long employeeId);
	
    /**
     * 특정 직원의 근태 이력 전체 조회 (비동기)
     * @param employeeId 직원 ID
     */
    List<ResAttendanceDTO> getAttendanceList(Long employeeId);
	
    /**
     * 출근 등록 (비동기)
     * @param reqDto 출근 정보
     */
    ResAttendanceDTO attendanceIn(ReqAttendanceDTO reqDto);

    /**
     * 퇴근 등록 (비동기)
     * @param attendanceId 근태 ID
     * @param reqDto       퇴근 정보
     */
    ResAttendanceDTO attendanceOut(Long employeeId);
}