package com.bank.app.usecase.official;

import com.bank.app.entity.client.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfficialDto {
    
    private String cpf;

    private String rg;

    private String nameComplete;

    private String email;

    private String password;

    private Address address;

}
