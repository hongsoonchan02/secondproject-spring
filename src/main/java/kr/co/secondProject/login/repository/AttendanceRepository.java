package kr.co.secondProject.login.repository;

import kr.co.secondProject.login.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // 특정 직원의 근태 기록 전체 조회 (최신순)
    // OrderByDateDesc → date 기준 내림차순 정렬 (최신꺼 먼저)
    List<Attendance> findByEmployee_IdOrderByDateDesc(Long employeeId);

    // 특정 직원의 오늘 근태 기록 조회
    // 날짜 딱 하나만 조회 (오늘 00:00:00)
    Optional<Attendance> findByEmployee_IdAndDate(Long employeeId, LocalDateTime date);

    // 이번달 출근 기록 조회
    // Between → start ~ end 사이 범위 조회
    // ex) 2026-03-01 00:00:00 ~ 2026-03-31 23:59:59
    List<Attendance> findByEmployee_IdAndDateBetween(Long employeeId,
                                                     LocalDateTime start,
                                                     LocalDateTime end);
}