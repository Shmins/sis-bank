package com.bank.app.usecase.email;

public record EmailAccountDto(String name, Boolean isApproved, String to, String subject, String typeSubject){
    
}
