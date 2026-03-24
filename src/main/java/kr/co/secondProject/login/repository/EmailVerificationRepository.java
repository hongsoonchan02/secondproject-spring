package kr.co.secondProject.login.repository;

import kr.co.secondProject.login.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmailAndVerificationCode(String email, String verificationCode);
}
