package vn.tdsoftware.hrm_backend.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    @Value("${mail.from-sender}")
    private String fromMail;

    @Value("${mail.company}")
    private String companyMail;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String toEmail, String username, String password) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromMail, companyMail);
            helper.setTo(toEmail);
            helper.setSubject("Kích hoạt tài khoản nhân viên");
            helper.setText("Tài khoản: " + username + "\n Mật khẩu: " + password);
            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
