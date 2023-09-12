package com.bank.app.entity.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone {
    private String ddd;
    private String number;

    public Phone(String number, String ddd) {
        if (number == null || ddd == null) {
            throw new IllegalArgumentException("Número de telefone vazio");
        }

        this.number = number;
        this.ddd = ddd;
    }
}
