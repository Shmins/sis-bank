package com.bank.app.entity.client.model;

import com.bank.app.entity.client.exception.GenericException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String typeAccount;

    public Account(String typeAccount){
        if(typeAccount == null){
            throw new IllegalArgumentException("Tipo de conta vazio");
        }
        if(!typeAccount.equals("chain") && !typeAccount.equals("savings")){
            throw new GenericException("Tipo de conta inválido");
        }
        this.typeAccount = typeAccount;
    }
}
