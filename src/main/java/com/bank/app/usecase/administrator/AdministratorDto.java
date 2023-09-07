package com.bank.app.usecase.administrator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdministratorDto {
    
    private String cpf;

    private String rg;

    private String nameComplete;

    private String bankAgency;

    private String password;

}
