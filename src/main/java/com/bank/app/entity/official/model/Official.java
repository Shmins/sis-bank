package com.bank.app.entity.official.model;

import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Password;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Official {
    private String cpf;
    private Password password;
    private Address address;
    private String email;
}
