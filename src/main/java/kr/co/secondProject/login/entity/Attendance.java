package kr.co.secondProject.login.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@Table(name = "attendance")
@AllArgsConstructor
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


    //퇴근처리 메서드
    //this -> 지금 이 객체 자신을 가리킴
    // 퇴근시각, 근무시간, 상태를 업그레이드
    public void checkOut(LocalDateTime endTime, String allTime, String state){
        this.endTime = endTime;
        this.allTime = allTime;
        this.state = state;
    }


}
