package kr.co.secondProject.login.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;      //근태코드 (PK)

    @ManyToOne
    @JoinColumn(name = "id")
    private Employee employee;      // 유저넘버(FK) :직원ID

    private LocalDateTime date;     // 날짜

    private LocalDateTime startTime;// 출근시간

    private LocalDateTime endTime;  // 퇴근시간

    private String allTime;         // 근무시간

    private String state;           // 근태상태

    // 근태 상태 계산
    public void checkOut(LocalDateTime endTime, LocalDateTime standardTime) {
        this.endTime = endTime;
        this.allTime = calculateAllTime(endTime);
        this.state   = calculateState(standardTime);
    }
 
    // 근무 시간 계산
    private String calculateAllTime(LocalDateTime endTime) {
        return String.valueOf(Duration.between(this.startTime, endTime).toMinutes());
    }
 
    // 출근 시각이 기준 시각 초과 → "지각" / 이하 → "정상"
    private String calculateState(LocalDateTime standardTime) {
        return this.startTime.isAfter(standardTime) ? "지각" : "정상";
    }
}
