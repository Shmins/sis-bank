package com.bank.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.client.model.email.EmailCode;
import com.bank.app.entity.client.repository.CodeEmailRepository;
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
    @Autowired 
    private CodeEmailTemplate codeEmailTemplate;
    @Autowired
    private CodeEmailRepository codeEmailRepository;

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
            EmailCode result = this.codeEmailRepository.save(new EmailCode(data.code()));
            return new ResponseEntity<>(result.getId(), HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(400));
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCode(@PathVariable("id") String id) {
        try {
            Optional<EmailCode> code = this.codeEmailRepository.findById(id);
            return new ResponseEntity<>(code.isPresent()? code.get(): null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(400));
        }

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.codeEmailRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
