package kr.co.secondProject.dashboard.service;


import kr.co.secondProject.dashboard.dto.AttendanceRecordDTO;
import kr.co.secondProject.dashboard.dto.CheckInResDTO;
import kr.co.secondProject.dashboard.dto.DashboardResDTO;
import kr.co.secondProject.login.entity.Attendance;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.AttendanceRepository;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    //======== 대시보드 전체 정보 조회 ==========
    public DashboardResDTO getDashboardInfo(Long employeeId) {

            //직원 정보 조회
            // orElseThrow -> 못찾으면 에러 던짐
            Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다."));

            //오늘 날짜를 00:00:00으로 변환
            LocalDateTime todayStart = LocalDateTime.now()
                    .toLocalDate()
                    .atStartOfDay();

            //오늘 출근 기록 조회
            // isPresent() ->값이 있으면 true. 없으면 false
            Optional<Attendance> todayRecord =
                attendanceRepository.findByEmployee_IdAndDate(employeeId, todayStart);

            boolean checkedInToday = todayRecord.isPresent();
            LocalDateTime todayStartTime = checkedInToday  //출근했으면 출근시간, 안했으면 null
                ? todayRecord.get().getStartTime()
                : null;

        //이번달 출근 횟수 계산
        YearMonth yearMonth = YearMonth.now();
        //atDay(1) -> 이번달 1일 00:00:00
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        //atEndOfMonth() -> 이번달 마지막날 23:59:59
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<Attendance> monthlyList =
                attendanceRepository.findByEmployee_IdAndDateBetween(employeeId,start, end);
        // .size() -> 리스트 개수 = 이번달 출근 횟수
        int monthlyWorkDays = monthlyList.size();

        //전체 출근 기록 목록 변환
        //stream() -> 리스트를 하나씩 꺼내서 처리
        //map() -> Attendance 객체를 AttendanceRecordDTO로 변환
        //collect() -> 변환된 것들을 다시 리스트로 모음
        List<AttendanceRecordDTO> attendanceList = attendanceRepository
                .findByEmployee_IdOrderByDateDesc(employeeId)
                .stream()
                .map(a -> AttendanceRecordDTO.builder()
                        .date(a.getDate())
                        .startTime(a.getStartTime())
                        .endTime(a.getEndTime())
                        .allTime(a.getAllTime())
                        .state(a.getState())
                        .build())
                .collect(Collectors.toList());

        return DashboardResDTO.builder()
                .name(employee.getName())
                .position(employee.getPosition())
                .role(employee.getRole())
                .checkedInToday(checkedInToday)
                .todayStartTime(todayStartTime)
                .monthlyWorkDays(monthlyWorkDays)
                .attendanceList(attendanceList)
                .build();
    }

    // ==== 출근 처리 ====
    public CheckInResDTO checkIn(Long employeeId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();

        //1. 중복 출근 방지 코드
        attendanceRepository.findByEmployee_IdAndDate(employeeId,todayStart)
                .ifPresent( a-> {
                    throw new RuntimeException("이미 출근 처리 되었습니다.")
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
    public void checkOut(Long employeeId) {

        LocalDateTime todayStart = LocalDateTime.now()
                .toLocalDate()
                .atStartOfDay();

        Attendance attendance = attendanceRepository
                .findByEmployee_IdAndDate(employeeId, todayStart)
                .orElseThrow(() -> new RuntimeException("출근 기록이 없습니다."));

        LocalDateTime now = LocalDateTime.now();

        // 근무시간 계산
        long hours = java.time.Duration.between(attendance.getStartTime(), now).toHours();
        long minutes = java.time.Duration.between(attendance.getStartTime(), now).toMinutesPart();
        String allTime = hours + "시간 " + minutes + "분";

        // 18시 이전 퇴근이면 조퇴, 이후면 정상
        String state = now.getHour() < 18 ? "조퇴" : "정상";

        attendance.checkOut(now, allTime, state);
        attendanceRepository.save(attendance);
    }
}
