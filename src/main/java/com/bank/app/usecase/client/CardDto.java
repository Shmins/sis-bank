package com.bank.app.usecase.client;

import com.bank.app.entity.client.model.cardmodel.TypeCard;

public record CardDto(String cvc, String nameComplete, TypeCard typeCard,String typeIssuer, String validityDate, boolean isActive) {
    
}
