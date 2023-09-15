package com.bank.app.usecase.client;

import java.util.List;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Phone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientDto {
    
    private String cpf;

    private String nameComplete;

    private String email;

    private String password;

    private List<Account> account;

    private Phone phone;

    private Address address;

    private Integer accountLimit;
}
