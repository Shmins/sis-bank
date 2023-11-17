package com.bank.app.entity.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String road;
    private int number;
    private String cep;
    private String district;
    private String city;
    private String state;
}
