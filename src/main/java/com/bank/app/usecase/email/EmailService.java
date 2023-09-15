package com.bank.app.usecase.email;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, EmailCodeDto data) throws MessagingException, UnsupportedEncodingException;
    void sendEmailAccount(EmailAccountDto data) throws MessagingException, UnsupportedEncodingException;

}
