package com.bank.app.entity.client.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Account")
public class Account {
    @Id
    private String id;
    private List<Card> cards = new ArrayList<>();
    private String typeAccount;
    private String cpf;
    private String nameComplete; // name of client /
    private Boolean isActive;

    public Account(String nameComplete, String typeAccount, String cpf){
         if(!typeAccount.equals("chain") && !typeAccount.equals("savings")){
            throw new GenericException("Tipo de conta inválido");
        }
        this.typeAccount = typeAccount;
        this.cpf = cpf;
        this.nameComplete = nameComplete;
    }
}
