package kr.co.secondProject.vacation.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Column(name = "annual_code")
	private int annualCode;		// 연차 코드
	
	private int joinYears;			// 입사 연수
	
	private int annualNum;			// 연처 갯수
}
