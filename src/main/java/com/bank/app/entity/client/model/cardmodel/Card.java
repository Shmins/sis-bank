package com.bank.app.entity.client.model.cardmodel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.bank.app.usecase.client.CardDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {

    @Id
    private String numberCard;
    
    @Indexed(unique = true)
    private int cvc;
 
    private String typeCard;

    private String nameComplete;

    private String typeIssuer; // Visa, MasterCard, Elo, Hibercard ou American Express

    private String validityDate;

    private boolean isAuthorized;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Card(String numberCard, int cvc, String typeCard, String nameComplete, String typeIssuer,
            String validityDate, Boolean isAuthorized){
        if (cvc <= 100) {
            throw new IllegalArgumentException("CVC com formato errado");
        }
        if (!numberCard.matches("\\d{4}\\ \\d{4}\\ \\d{4}\\ \\d{4}")) {
            throw new IllegalArgumentException("Número de cartão inválido");
        }
        if(typeCard == null){
            throw new IllegalArgumentException("Tipo de cartão vazio");
        }
        if(!typeCard.equals("credit") && !typeCard.equals("debit") && !typeCard.equals("savings") && !typeCard.equals("credit_debit")&& !typeCard.equals("savings_debit")){
            throw new IllegalArgumentException("Tipo de cartão inválido");
        }
        this.nameComplete = nameComplete;
        this.typeCard = typeCard;
        this.typeIssuer = typeIssuer;

        this.numberCard = numberCard;
        this.cvc = cvc;
        this.validityDate = validityDate;
        this.isAuthorized = isAuthorized;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
    public Card(CardDto data){
        this.numberCard = data.numberCard();
        this.cvc = data.cvc();
        this.nameComplete = data.nameComplete();
        this.typeIssuer = data.typeIssuer();
        this.typeCard = data.typeCard();
        this.validityDate = data.validityDate();
        this.isAuthorized = data.isActive();
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
