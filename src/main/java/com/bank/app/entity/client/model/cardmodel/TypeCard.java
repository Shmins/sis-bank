package com.bank.app.entity.client.model.cardmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeCard {
    private String type;

    public TypeCard(String type){
        if(type == null){
            throw new IllegalArgumentException("Tipo de cartão invalido");
        }
        this.type = type;
    }
}
