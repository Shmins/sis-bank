package com.bank.app.infrastructure.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.email.EmailAccountDto;
import com.bank.app.usecase.email.EmailCodeDto;
import com.bank.app.usecase.email.EmailService;
import com.bank.app.usecase.email.template.NotifyEmailTemplate;
import com.bank.app.usecase.email.template.CodeEmailTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender email;
    @Autowired
    private CodeEmailTemplate codeEmailTemplate;
    @Autowired 
    private NotifyEmailTemplate accountEmailTemplate;

    @Override
    public void sendEmail(String to, String subject, EmailCodeDto data)
            throws MessagingException, UnsupportedEncodingException {

        String code = String.valueOf(data.code());

        code = code.substring(0, 3) + "-" + code.substring(3, 6);

        String template = this.codeEmailTemplate.getTemplate(data.name(), code);

        MimeMessage messages = this.email.createMimeMessage();

        messages.setFrom(new InternetAddress("shmins.156@gmail.com", "SwiftPay"));
        messages.setRecipients(MimeMessage.RecipientType.TO, to);
        messages.setSubject(subject);
        messages.setContent(template, "text/html; charset=utf-8");

        this.email.send(messages);
    }

    @Override
    public void sendEmailAccount(EmailAccountDto data)
            throws MessagingException, UnsupportedEncodingException {


        String template = this.accountEmailTemplate.getTemplate(data.name(), data.isApproved(), data.typeSubject());

        MimeMessage messages = this.email.createMimeMessage();

        messages.setFrom(new InternetAddress("shmins.156@gmail.com", "SwiftPay"));
        messages.setRecipients(MimeMessage.RecipientType.TO, data.to());
        messages.setSubject(data.subject());
        messages.setContent(template, "text/html; charset=utf-8");

        this.email.send(messages);
    }

}
