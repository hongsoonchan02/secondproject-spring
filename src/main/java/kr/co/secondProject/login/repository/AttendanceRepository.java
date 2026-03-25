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
    List<Attendance> findByEmployeeIdOrderByDateDesc(Long employeeId);

    //특정 직원의 오늘 근태 기록 조회
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDateTime date);

    //이번달 출근 기록 조회 (년도 +월로 필터)
    // Year(a.date), MONTH(a.date) -> date 컬럼에서 년도/월만 추출해서 비교
    @Query("SELECT a FROM Attendance a WHERE a.employee.id =: employeeId "+
            "AND YEAR(a.date) = :year AND MONTH(a.date) =: month")
    List<Attendance> findMonthlyAttendance(@Param("employeeId") Long employeeId,
                                           @Param("year") int year,
                                           @Param("month") int month);

}
