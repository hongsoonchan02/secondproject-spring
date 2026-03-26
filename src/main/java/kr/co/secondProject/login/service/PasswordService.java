package kr.co.secondProject.login.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.secondProject.login.dto.ResetPasswordReqDTO;
import kr.co.secondProject.login.dto.VerifyCodeReqDTO;
import kr.co.secondProject.login.entity.EmailVerification;
import kr.co.secondProject.login.entity.Employee;
import kr.co.secondProject.login.repository.EmailVerificationRepository;
import kr.co.secondProject.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final EmployeeRepository employeeRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    // 1. 인증코드 생성 + 이메일 발송
    public void sendVerificationCode(String email) {

        // 직원 존재 여부 확인
        employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        // 6자리 랜덤 인증코드 생성
        String code = String.format("%06d", new Random().nextInt(999999));

        // EmailVerification 저장
        EmailVerification verification = new EmailVerification(
                email, code, LocalDateTime.now().plusMinutes(3)
        );
        emailVerificationRepository.save(verification);

        // 이메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Architect Ledger] 비밀번호 찾기 인증코드");
        message.setText("인증코드: " + code + "\n3분 안에 입력해주세요.");
        mailSender.send(message);
    }

    // 2. 인증코드 확인
    public boolean verifyCode(VerifyCodeReqDTO req) {
        EmailVerification verification = emailVerificationRepository
                .findByEmailAndVerificationCode(req.getEmail(), req.getVerificationCode())
                .orElseThrow(() -> new RuntimeException("인증코드가 틀렸습니다."));

        // 3분 지났는지 확인
        if (verification.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("인증코드가 만료됐습니다.");
        }

        return true;
    }

    // 3. 새 비밀번호 설정
    public void resetPassword(ResetPasswordReqDTO req) {
        Employee employee = employeeRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        employee.updatePassword(req.getNewPassword());
        employeeRepository.save(employee);
    }
}