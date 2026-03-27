package kr.co.secondProject.attendance.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.secondProject.attendance.dto.AttendanceStatsDto;
import kr.co.secondProject.attendance.dto.ReqAttendanceDTO;
import kr.co.secondProject.attendance.dto.ResAttendanceDTO;
import kr.co.secondProject.attendance.sevice.AttendanceService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    // [GET] 이번 달 근태 통계 조회
    // 화면 : Attendance.jsx 상단 stat-card 4개 (출근일수/지각/결근/근태점수)
    // URL  : GET /attendance/{employeeId}/stats
    // ────────────────────────────────────────────────────────────────────────
    @GetMapping("/{employeeId}/stats")
    public ResponseEntity<AttendanceStatsDto> getAttendanceStats(
            @PathVariable Long employeeId) {
 
        return ResponseEntity.ok(attendanceService.getAttendanceStats(employeeId));
    }
    
    
    // [GET] 근태 이력 전체 조회
    // URL  : GET /attendance/{employeeId}
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<ResAttendanceDTO>> getAttendanceList(
            @PathVariable Long employeeId) {
 
        return ResponseEntity.ok(attendanceService.getAttendanceList(employeeId));
    }

    
    // [POST] 출근 등록
    // URL  : POST /attendance
    // Body : { employeeId, date, startTime, state }
    @PostMapping("/")
    public ResponseEntity<ResAttendanceDTO> AttendanceIn(
            @RequestBody ReqAttendanceDTO reqDto) {
 
        return ResponseEntity.ok(attendanceService.AttendanceIn(reqDto));
    }

    // [POST] 퇴근 등록
    // URL  : PATCH /attendance/{attendanceId}
    // Body : { endTime }
    @PatchMapping("/{attendanceId}")
    public ResponseEntity<ResAttendanceDTO> AttendanceOut(
            @PathVariable Long attendanceId,
            @RequestBody ReqAttendanceDTO reqDto) {
 
        return ResponseEntity.ok(attendanceService.AttendanceOut(attendanceId, reqDto));
    }
}