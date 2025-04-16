package vn.tdsoftware.hrm_backend.common.thread;

import jakarta.mail.MessagingException;
import vn.tdsoftware.hrm_backend.common.service.EmailService;

public class SendMailThread implements Runnable {
    private final EmailService emailService;
    private final String toEmail;
    private final String username;
    private final String password;

    public SendMailThread(EmailService emailService, String toEmail, String username, String password) {
        this.emailService = emailService;
        this.toEmail = toEmail;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        try {
            emailService.sendEmail(toEmail, username, password);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
