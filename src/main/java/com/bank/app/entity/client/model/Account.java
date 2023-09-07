package com.bank.app.entity.client.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String nameComplete; // name of client / stranger key

    public Account(List<Card> cards, String nameComplete){
        this.cards = cards;
        this.nameComplete = nameComplete;
    }
}
