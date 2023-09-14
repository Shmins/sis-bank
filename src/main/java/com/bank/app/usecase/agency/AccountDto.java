package com.bank.app.usecase.agency;

import com.bank.app.entity.client.model.NumberAgency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {

    private String typeAccount;

    private NumberAgency numberAgency;

    private String cpf;
}
