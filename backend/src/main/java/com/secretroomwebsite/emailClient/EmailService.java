package com.secretroomwebsite.emailClient;

import com.secretroomwebsite.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.copyTo}")
    private List<String> copyTo;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);

            for (String cc : copyTo) {
                helper.addCc(cc);
            }

            helper.setSubject(subject);
            helper.setText(text, true);
            helper.addInline("vs_logo", new ClassPathResource("static/images/vs_logo.png"));

            emailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new EmailSendingException("Failed to send email", e);
        }
    }
}