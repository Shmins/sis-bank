package com.bank.app.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Client {
    @Id
    private String id;
    private String nameComplete;
    private String email;
    private String password;
    private int cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
