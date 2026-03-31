package kr.co.secondProject.attendance.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.secondProject.attendance.dto.AttendanceStatsDto;
import kr.co.secondProject.attendance.dto.ReqAttendanceDTO;
import kr.co.secondProject.attendance.dto.ResAttendanceDTO;
import kr.co.secondProject.attendance.sevice.AttendanceService;
import kr.co.secondProject.login.entity.Attendance;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.AttendanceRepository;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository   employeeRepository;


    // 이번 달 근태 통계 조회
    //  - 출근 일수 : state = "정상" 또는 "지각"
    //  - 지각 횟수 : state = "지각"
    //  - 결근 일수 : state = "결근"
    //  - 근태 점수 : (출근 일수 / 이번 달 총 일수) × 100
    @Override
    @Transactional(readOnly = true)
    public AttendanceStatsDto getAttendanceStats(Long employeeId) {

        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth   = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        List<Attendance> monthList = attendanceRepository
                .findByEmployee_IdAndDateBetween(employeeId, startOfMonth, endOfMonth);

        int workDays = (int) monthList.stream()
                .filter(a -> "정상".equals(a.getState()) || "지각".equals(a.getState()))
                .count();

        int lateDays = (int) monthList.stream()
                .filter(a -> "지각".equals(a.getState()))
                .count();

        int absentDays = (int) monthList.stream()
                .filter(a -> "결근".equals(a.getState()))
                .count();

        int    totalDays = YearMonth.now().lengthOfMonth();
        double score     = totalDays == 0
                ? 0
                : Math.round((workDays / (double) totalDays) * 1000.0) / 10.0;

        return new AttendanceStatsDto(workDays, lateDays, absentDays, score);
    }


    // 근태 이력 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<ResAttendanceDTO> getAttendanceList(Long employeeId) {

        return attendanceRepository
                .findByEmployee_IdOrderByDateDesc(employeeId)
                .stream()
                .map(ResAttendanceDTO::from)
                .collect(Collectors.toList());
    }


    // 출근 등록
    @Override
    @Transactional  // readOnly = false (쓰기 작업 override)
    public ResAttendanceDTO AttendanceIn(ReqAttendanceDTO reqDto) {

        Employee employee = employeeRepository.findById(reqDto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "직원을 찾을 수 없습니다. ID: " + reqDto.getEmployeeId()));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(reqDto.getDate());
        attendance.setStartTime(reqDto.getStartTime());
        attendance.setState("출근중");  // 퇴근 전 임시 상태

        Attendance saved = attendanceRepository.save(attendance);
        return ResAttendanceDTO.from(saved);
    }


    // 퇴근 등록
    //  - 퇴근 시간 저장 후 근무시간 및 근태 상태 자동 계산
    //  - 지각 기준: 출근 시각 09:00 초과 → "지각", 이하 → "정상"
    @Override
    @Transactional  // readOnly = false (쓰기 작업 override)
    public ResAttendanceDTO AttendanceOut(Long attendanceId, ReqAttendanceDTO reqDto) {

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
        return ResAttendanceDTO.from(saved);
    }



}