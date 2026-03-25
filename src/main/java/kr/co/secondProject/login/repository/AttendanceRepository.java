package kr.co.secondProject.login.repository;

import kr.co.secondProject.login.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    //특정 직원의 근태 기록 전체 조회
    List<Attendance> findByEmployeeId(Long employeeId);

    //특정 직원의 오늘 근태 기록 조회
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDateTime date);

    // 특정 직원의 특정 기간 근태 기록 조회 (월별 조회 등에 활용)
    List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);

}
