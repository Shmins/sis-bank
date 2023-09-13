package com.bank.app.entity.client.model.email;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "Codes")
public class EmailCode {
    @Id
    private String id;

    private int code;
    
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public EmailCode(int code){
        this.code = code;

        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}
