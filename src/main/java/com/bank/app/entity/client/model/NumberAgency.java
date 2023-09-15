package com.bank.app.entity.client.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "agency")
public class NumberAgency {
        
    private String number;

    private String nameAgency;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public NumberAgency(String number, String nameAgency){
        this.nameAgency = nameAgency;
        this.number = number;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
