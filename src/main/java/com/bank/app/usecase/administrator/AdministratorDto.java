package com.bank.app.usecase.administrator;

import com.bank.app.entity.client.model.NumberAgency;

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

    private NumberAgency bankAgency;

    private String password;

}
