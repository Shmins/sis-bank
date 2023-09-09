package com.bank.app.usecase.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BossDto {
    
    private String cpf;

    private String nameComplete;

    private String password;

}
