package kr.co.secondProject.attendance.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kr.co.secondProject.attendance.dto.ReqAttendanceDTO;
import kr.co.secondProject.attendance.dto.ResAttendanceDTO;
import kr.co.secondProject.attendance.sevice.AttendanceService;
import kr.co.secondProject.login.entity.Attendance;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.AttendanceRepository;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;


@Service  // 빈 등록 
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository   employeeRepository;

    
    
 // 근태 이력 전체 조회 (비동기 처리)
    @Async
    @Override
    public CompletableFuture<List<ResAttendanceDTO>> getAttendanceList(Long employeeId) {
        List<Attendance> list = attendanceRepository.findByEmployee_IdOrderByDateDesc(employeeId);
 
        List<ResAttendanceDTO> result = list.stream()
							                .map(this::toResDto)
							                .collect(Collectors.toList());
 
        return CompletableFuture.completedFuture(result);
    }

    
    
    // 출근 등록 (비동기 처리)
    @Async
    @Override
    public CompletableFuture<ResAttendanceDTO> checkIn(ReqAttendanceDTO reqDto) {
 
        Employee employee = employeeRepository.findById(reqDto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "직원을 찾을 수 없습니다. ID: " + reqDto.getEmployeeId()));
 
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(reqDto.getDate());
        attendance.setStartTime(reqDto.getStartTime());
        attendance.setState("출근중");  // 퇴근 전 임시 상태
 
        Attendance saved = attendanceRepository.save(attendance);
        return CompletableFuture.completedFuture(toResDto(saved));
    }
    
    
    // 퇴근 등록 (비동기 처리)
    //  - 퇴근 시간 저장 후 근무시간 및 근태 상태 자동 계산
    //  - 지각 기준: 출근 시각 09:00 초과 → "지각", 이하 → "정상"
    @Async
    @Override
    public CompletableFuture<ResAttendanceDTO> checkOut(Long attendanceId, ReqAttendanceDTO reqDto) {
 
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "근태 기록을 찾을 수 없습니다. ID: " + attendanceId));
 
        attendance.setEndTime(reqDto.getEndTime());
 
        // 근무 시간 계산
        if (attendance.getStartTime() != null && reqDto.getEndTime() != null) {
            Duration duration = Duration.between(attendance.getStartTime(), reqDto.getEndTime());
            long hours   = duration.toHours();
            long minutes = duration.toMinutesPart();
            attendance.setAllTime(hours + "시간 " + minutes + "분");
        }
 
        // 지각 여부 판단 (기준 시간 변경 시 아래 atTime 수정)
        if (attendance.getStartTime() != null) {
            LocalDateTime standardTime = attendance.getDate().toLocalDate().atTime(9, 0);
            attendance.setState(
                    attendance.getStartTime().isAfter(standardTime) ? "지각" : "정상");
        }
 
        Attendance saved = attendanceRepository.save(attendance);
        return CompletableFuture.completedFuture(toResDto(saved));
    }
 
    // Entity → ResAttendanceDTO 변환
    private ResAttendanceDTO toResDto(Attendance attendance) {
        ResAttendanceDTO dto = new ResAttendanceDTO();
        dto.setAttendanceId(attendance.getAttendanceId());
        dto.setDate(attendance.getDate());
        dto.setStartTime(attendance.getStartTime());
        dto.setEndTime(attendance.getEndTime());
        dto.setAllTime(attendance.getAllTime());
        dto.setState(attendance.getState());
 
        if (attendance.getEmployee() != null) {
            dto.setEmployeeId(attendance.getEmployee().getId());
            dto.setEmployeeName(attendance.getEmployee().getName());
        }
        return dto;
    }
}