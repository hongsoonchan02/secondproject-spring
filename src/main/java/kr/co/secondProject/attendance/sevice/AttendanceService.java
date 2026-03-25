package kr.co.secondProject.attendance.sevice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import kr.co.secondProject.attendance.dto.ReqAttendanceDTO;
import kr.co.secondProject.attendance.dto.ResAttendanceDTO;



public interface AttendanceService {

    /**
     * 출근 등록 (비동기)
     * @param reqDto 출근 정보
     */
    CompletableFuture<ResAttendanceDTO> checkIn(ReqAttendanceDTO reqDto);

    /**
     * 퇴근 등록 (비동기)
     * @param attendanceId 근태 ID
     * @param reqDto       퇴근 정보
     */
    CompletableFuture<ResAttendanceDTO> checkOut(Long attendanceId, ReqAttendanceDTO reqDto);
}