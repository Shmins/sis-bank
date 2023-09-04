package com.bank.app.entity.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String road;
    private int number;
    private String cep;
    private String district;
    private String city;
    private String state;

    public Address(String road, int number, String cep, String district, String city, String state){
       
        this.road = road;
        this.cep = cep;
        this.number = number;
        this.city = city;
        this.district = district;
        this.state = state;
    }
}
