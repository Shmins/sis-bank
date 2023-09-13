package com.bank.app.infrastructure.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.email.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender email;
    @Override
    public void sendEmail(String to, String subject, String message) throws MessagingException, UnsupportedEncodingException {
        MimeMessage  messages = this.email.createMimeMessage();

        messages.setFrom(new InternetAddress("shmins.156@gmail.com", "SwiftPay"));
        messages.setRecipients(MimeMessage.RecipientType.TO, to);
        messages.setSubject(subject);
        messages.setContent(message,"text/html; charset=utf-8");

        this.email.send(messages);
    }
    
}
