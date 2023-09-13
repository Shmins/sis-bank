package com.bank.app.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.email.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender email;
    @Override
    public void sendEmail(String to, String subject, String message) throws MessagingException {
        MimeMessage  messages = this.email.createMimeMessage();

        messages.setFrom("<Shmins> shmins.156@gmail.com");
        messages.setRecipients(MimeMessage.RecipientType.TO, to);
        messages.setSubject(subject);
        messages.setContent(message,"text/html; charset=utf-8");

        email.send(messages);
    }
    
}
