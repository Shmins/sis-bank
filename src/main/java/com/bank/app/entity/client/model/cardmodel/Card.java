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

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Card(String numberCard, int cvc, TypeCard typeCard, String nameComplete, String typeIssuer,
            String validityDate){
        if (cvc <= 100) {
            throw new IllegalArgumentException("CVC com formato errado");
        }
        if (!numberCard.matches("\\d{4}\\ \\d{4}\\ \\d{4}\\ \\d{4}")) {
            throw new IllegalArgumentException("Número de cartão inválido");
        }
        if(!numberCard.substring(0, 2).equals("51") || !numberCard.substring(0, 2).equals("55") //MasterCard
        && !numberCard.substring(0, 1).equals("4") //Visa
        && !numberCard.substring(0, 2).equals("34") || !numberCard.substring(0, 2).equals("37") 
        && !numberCard.substring(0, 1).equals("3")
        && !numberCard.equals("American_Express")){
            throw new IllegalArgumentException("Tipo de cartão inválido");
        }
        this.nameComplete = nameComplete;
        this.typeCard = typeCard;
        this.typeIssuer = typeIssuer;

        this.numberCard = numberCard;
        this.cvc = cvc;
        this.validityDate = validityDate;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
