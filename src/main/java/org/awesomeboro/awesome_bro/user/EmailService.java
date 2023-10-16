package org.awesomeboro.awesome_bro.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendTempPassword(String email, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("임시 비밀번호 발급합니다.");
        message.setText("임시 비밀번호는 " + tempPassword + " 입니다.");
        message.setFrom("rhksdud23000@naver.com"); // from을 붙여야 권한 에러 없이 이메일 보내짐.
        javaMailSender.send(message);
    }
}
