package com.bank.app.entity.client.model.cardmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeCard {
    private String type;

    public TypeCard(String type){
        if(type == null){
            throw new IllegalArgumentException("Tipo de cartão vazio");
        }
        if(!type.equals("credit") && !type.equals("debit") && !type.equals("savings") && !type.equals("credit_debit")&& !type.equals("savings_debit")){
            throw new IllegalArgumentException("Tipo de cartão inválido");
        }
        this.type = type;
    }
}
