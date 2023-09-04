package com.bank.app.entity.client.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bank.app.entity.client.exception.GenericException;

import lombok.Data;

@Data
public class Password {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String code;

    public Password(String password){
        if(password.isEmpty()){
            throw new GenericException("Senha vazia");
        }
        if(password.length() < 8){
            throw new GenericException("Senha com menos de 8 digitos");
        }
       this.code = this.passwordEncoder.encode(password);
    }
}
