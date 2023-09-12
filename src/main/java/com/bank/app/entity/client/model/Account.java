package com.bank.app.entity.client.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    @Id
    private String id;

    private List<Card> cards = new ArrayList<>();

    private String typeAccount;

    private NumberAgency numberAgency;

    private Boolean isActive;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Account(String typeAccount, NumberAgency numberAgency){
         if(!typeAccount.equals("chain") && !typeAccount.equals("savings")){
            throw new GenericException("Tipo de conta inválido");
        }
        this.numberAgency = numberAgency;
        this.typeAccount = typeAccount;
        this.isActive = false;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
