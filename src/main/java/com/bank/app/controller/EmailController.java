package com.bank.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.usecase.email.CodeEmailTemplate;
import com.bank.app.usecase.email.EmailCodeDto;
import com.bank.app.usecase.email.EmailService;

@Controller
@RestController
@RequestMapping("email/v1")
@CrossOrigin(origins = "*")
public class EmailController {
    @Autowired
    private EmailService emailSend;
    @Autowired CodeEmailTemplate codeEmailTemplate;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> sendEmail(@RequestBody EmailCodeDto data ) {
 
        String code = String.valueOf(data.code());

        code = code.substring(0, 3) + "-" + code.substring(3, 6);

        String template = this.codeEmailTemplate.getTemplate(data.name(), code);
        try {
            this.emailSend.sendEmail(
                 data.to(), 
                 data.subject(), 
                 template);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }
}
