package com.bank.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.usecase.email.EmailService;

@Controller
@RestController
@RequestMapping("email/v1")
@CrossOrigin(origins = "*")
public class EmailController {
    @Autowired
    private EmailService emailSend;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> sendEmail() {
        try {
            this.emailSend.sendEmail("shmins.156@gmail.com", "Shmins", "alysonpedro.144@gmail.com", "Pedro", "<b>tu é gostoso</b>");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }
}
