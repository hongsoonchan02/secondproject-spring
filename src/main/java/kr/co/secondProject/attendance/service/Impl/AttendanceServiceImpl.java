package kr.co.secondProject.attendance.service.Impl;

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
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
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
    //  - 오늘 이미 출근 기록이 있으면 예외 (중복 출근 방지)
    //  - 9시 이상 → "지각" / 미만 → "정상"
    @Override
    @Transactional
    public ResAttendanceDTO attendanceIn(ReqAttendanceDTO reqDto) {
 
        LocalDateTime now        = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
 
        // 중복 출근 방지
        attendanceRepository.findByEmployee_IdAndDate(reqDto.getEmployeeId(), todayStart)
                .ifPresent(a -> {
                    throw new RuntimeException("이미 출근 처리 되었습니다.");
                });
 
        Employee employee = employeeRepository.findById(reqDto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "직원을 찾을 수 없습니다. ID: " + reqDto.getEmployeeId()));
 
        // 9시 이상이면 지각, 미만이면 정상
        String state = now.getHour() >= 9 ? "지각" : "정상";
 
        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(todayStart)
                .startTime(now)
                .state(state)
                .build();
 
        return ResAttendanceDTO.from(attendanceRepository.save(attendance));
    }
 
 
    // 퇴근 등록
    //  - 오늘 출근 기록을 employeeId로 조회
    //  - 퇴근 시각·근무시간·근태 상태 계산은 Attendance.checkOut() 에 위임
    //  - 지각 기준 시각(09:00)은 Service에서 생성해 엔티티에 전달
    @Override
    @Transactional
    public ResAttendanceDTO attendanceOut(Long employeeId) {
 
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
 
        Attendance attendance = attendanceRepository
                .findByEmployee_IdAndDate(employeeId, todayStart)
                .orElseThrow(() -> new RuntimeException("출근 기록이 없습니다."));
 
        LocalDateTime now          = LocalDateTime.now();
        LocalDateTime standardTime = attendance.getDate().toLocalDate().atTime(9, 0);
 
        attendance.checkOut(now, standardTime);
 
        return ResAttendanceDTO.from(attendanceRepository.save(attendance));
    }


}