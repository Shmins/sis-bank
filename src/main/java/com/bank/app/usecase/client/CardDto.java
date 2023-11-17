package com.bank.app.usecase.client;


public record CardDto(String numberCard, Integer cvc, String nameComplete, String typeCard,String typeIssuer, String validityDate, boolean isActive) {
    
}
