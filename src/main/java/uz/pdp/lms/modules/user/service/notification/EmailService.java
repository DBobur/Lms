package uz.pdp.lms.modules.user.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  /*  private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String resetLink = "https://your-domain.com/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Click the link to reset your password: " + resetLink);

        mailSender.send(message);
    }*/
}

