package kr.co.secondProject.login.repository;

import kr.co.secondProject.login.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    //특정 직원의 근태 기록 전체 조회 (최신순)
    List<Attendance> findByEmployee_IdOrderByDateDesc(Long employeeId);

    //특정 직원의 오늘 근태 기록 조회
    Optional<Attendance> findByEmployee_IdAndDate(Long employeeId, LocalDateTime date);

    //이번달 출근 기록 조회 (년도 +월로 필터)
    List<Attendance> findByEmployee_IdAndDateBetween(Long employeeId,
                                           LocalDateTime start, LocalDateTime end);

}
