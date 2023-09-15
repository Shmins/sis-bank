package com.bank.app.entity.client.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private String id;

    private List<Card> cards = new ArrayList<>();

    private String typeAccount;

    private NumberAgency numberAgency;

    private String cpf;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Account(String typeAccount, NumberAgency numberAgency, String cpf){
        if(!typeAccount.equals("chain") && !typeAccount.equals("savings")){
            throw new GenericException("Tipo de conta inválido");
        }
        this.id = UUID.randomUUID().toString();
        this.numberAgency = numberAgency;
        this.cpf = cpf;
        this.typeAccount = typeAccount;

        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
