package com.bank.app.entity.client.model.cardmodel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Card {

    @Id
    private String numberCard;
    
    @Indexed(unique = true)
    private int cvc;
 
    private TypeCard typeCard;

    private String nameComplete;

    private String typeIssuer; // Visa, MasterCard, Elo, Hibercard ou American Express

    private String validityDate;

    private boolean isActive;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Card(String numberCard, int cvc, TypeCard typeCard, String nameComplete, String typeIssuer,
            String validityDate, Boolean isActive){
        if (cvc <= 100) {
            throw new IllegalArgumentException("CVC com formato errado");
        }
        if (!numberCard.matches("\\d{4}\\ \\d{4}\\ \\d{4}\\ \\d{4}")) {
            throw new IllegalArgumentException("Número de cartão inválido");
        }
        this.nameComplete = nameComplete;
        this.typeCard = typeCard;
        this.typeIssuer = typeIssuer;

        this.numberCard = numberCard;
        this.cvc = cvc;
        this.validityDate = validityDate;
        this.isActive = isActive;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
