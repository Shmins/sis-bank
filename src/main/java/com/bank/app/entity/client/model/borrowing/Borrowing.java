package com.bank.app.entity.client.model.borrowing;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "borrowing")
public class Borrowing {
    @Id
    private String id;
    private String cpf; // Cpf of the user who requested the loan
    private int quantity; // Quantity -> 30000 = R$ 300,00
    private Boolean isAuthorized = false;
    private Boolean isRefused = false;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Borrowing(String cpf, int quantity) {
        this.cpf = cpf;
        this.quantity = quantity;
        this.updateAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
