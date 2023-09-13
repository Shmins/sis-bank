package com.bank.app.usecase.email;

public record EmailCodeDto(String name, int code, String to, String subject) {
    
}
