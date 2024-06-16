package org.example.promenergosvet.service.user;

import org.example.promenergosvet.entity.user.PasswordResetToken;
import org.example.promenergosvet.repo.user.PasswordResetTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Value("${spring.mail.username}")
    private String email;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public PasswordResetToken findByToken (String token) {
        return passwordResetTokenRepo.findByToken(token);
    }

}