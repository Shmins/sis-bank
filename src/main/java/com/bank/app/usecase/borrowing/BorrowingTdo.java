package com.bank.app.usecase.borrowing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BorrowingTdo {
    private String cpf; // Cpf of the user who requested the loan
    private int quantity; // Quantity -> 30000 = R$ 300,00
    private Boolean isAuthorized = false;
    private Boolean isRefused = false;
}
