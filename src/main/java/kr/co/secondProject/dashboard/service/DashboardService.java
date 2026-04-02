package kr.co.secondProject.dashboard.service;


import org.springframework.transaction.annotation.Transactional;
import kr.co.secondProject.dashboard.dto.AttendanceRecordDTO;
import kr.co.secondProject.dashboard.dto.CheckInResDTO;
import kr.co.secondProject.dashboard.dto.DashboardResDTO;
import kr.co.secondProject.login.entity.Attendance;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.AttendanceRepository;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

//    @Transactional(readOnly = true)
//    public DashboardResDTO getDashboardInfo(Long employeeId) {
//
//            Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다."));
//
//            LocalDateTime todayStart = LocalDateTime.now()
//                    .toLocalDate()
//                    .atStartOfDay();
//
//            Optional<Attendance> todayRecord =
//                attendanceRepository.findByEmployee_IdAndDate(employeeId, todayStart);
//
//            boolean checkedInToday = todayRecord.isPresent();
//            LocalDateTime todayStartTime = checkedInToday
//                ? todayRecord.get().getStartTime()
//                : null;
//
//        YearMonth yearMonth = YearMonth.now();
//
//        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
//        //atEndOfMonth() -> 이번달 마지막날 23:59:59
//        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
//
//        int monthlyWorkDays =
//                attendanceRepository.countByEmployee_IdAndDateBetween(employeeId,start, end);
//
//
//        //전체 출근 기록 목록 변환
//        List<AttendanceRecordDTO> attendanceList = attendanceRepository
//                .findByEmployee_IdAndDateBetween(employeeId,start,end)
//                .stream()
//                .map(AttendanceRecordDTO::from)
//                .toList();
//
//        return DashboardResDTO.of(
//                employee,
//                checkedInToday,
//                todayStartTime,
//                monthlyWorkDays,
//                attendanceList
//        );
//    }

    // ==== 출근 처리 ====
    @Transactional
    public CheckInResDTO checkIn(Long employeeId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();

        //1. 중복 출근 방지 코드
        attendanceRepository.findByEmployee_IdAndDate(employeeId,todayStart)
                .ifPresent( a-> {
                    throw new RuntimeException("이미 출근 처리 되었습니다.");
                });

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다."));

        //9시 이후면 지각, 이전이면 정상
        String state = now.getHour() >= 9? "지각" : "정상";

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .startTime(now)
                .date(todayStart)
                .state(state)
                .build();

        attendanceRepository.save(attendance);

        // 4. return
        return CheckInResDTO.builder()
                .startTime(now)
                .date(now.toLocalDate().toString())
                .state(state)
                .build();
    }
    // ===== 퇴근 처리 =====
    @Transactional
    public void checkOut(Long employeeId) {

        LocalDateTime todayStart = LocalDateTime.now()
                .toLocalDate()
                .atStartOfDay();

        Attendance attendance = attendanceRepository
                .findByEmployee_IdAndDate(employeeId, todayStart)
                .orElseThrow(() -> new RuntimeException("출근 기록이 없습니다."));

        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(attendance.getStartTime(), now);
        String totalMinutes  =String.valueOf(duration.toMinutes());

        String state = now.getHour() < 18 ? "조퇴": "정상";

//        attendance.checkOut(now, totalMinutes, state);
    }
}
