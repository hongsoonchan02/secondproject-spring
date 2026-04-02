package kr.co.secondProject.login.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
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
    private Long allTime;         // 근무시간
    private String state;           // 근태상태

    //퇴근 처리 메서드 <- 추가
    //this -> 지금 이 객체 자신을 가리킴
    //퇴근시각, 근무시간, 상태를 업데이트
    public void checkOut(LocalDateTime endTime, Long allTime, String state){
        this.endTime =endTime;
        this.allTime = allTime;
        this.state =state;
    }
}
