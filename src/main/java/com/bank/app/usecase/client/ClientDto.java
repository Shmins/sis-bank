package com.bank.app.usecase.client;

import java.util.List;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Phone;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    
    private String cpf;

    private String nameComplete;

    private String email;

    private String password;

    private List<Card> cards;

    private List<Account> account;

    private Phone phone;

    private Address address;

    private Integer accountLimit;

    private String role;
}
