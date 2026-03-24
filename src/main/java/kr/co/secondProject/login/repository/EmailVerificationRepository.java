package kr.co.secondProject.login.repository;

import kr.co.secondProject.login.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

}
