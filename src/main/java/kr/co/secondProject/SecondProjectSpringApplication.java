package kr.co.secondProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SecondProjectSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecondProjectSpringApplication.class, args);
	}
}