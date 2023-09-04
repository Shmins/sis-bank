package com.bank.app.usecase.client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Phone;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    
    private String cpf;

    private String nameComplete;

    private String email;

    private String password;

    private Account account;

    private Phone phone;

    private Address address;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<Card> cards = new ArrayList<>();

    public ClientDto(String cpf, String nameComplete, String email, String password, Account account, Phone phone, Address address) {
        this.cpf = cpf;
        this.nameComplete = nameComplete;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.account = account;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
