package kr.co.secondProject.vacation.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "annual")
public class Annual {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JoinColumn(name="annual_code")
	private String annualCode;		// 연차 코드
	
	private int joinYears;			// 입사 연수
	
	private int annualNum;			// 연처 갯수
}
