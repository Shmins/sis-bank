package com.bank.app.usecase.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {

    private String typeAccount;

    private String nameComplete;
     // name of client /
    private String cpf;

}
