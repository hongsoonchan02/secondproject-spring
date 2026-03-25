package kr.co.secondProject.login.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
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


}
