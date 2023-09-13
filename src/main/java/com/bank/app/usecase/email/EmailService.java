package com.bank.app.usecase.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String from, String name, String to, String subject, String message) throws MessagingException;
}
