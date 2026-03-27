package kr.co.secondProject.login.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Employee employee;

    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String allTime;
    private String state;

    public void checkOut(LocalDateTime endTime, String allTime, String state){
        this.endTime = endTime;
        this.allTime = allTime;
        this.state = state;
    }
}