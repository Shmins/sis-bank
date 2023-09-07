package com.bank.app.entity.administrator.model.approve;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Approve")
@AllArgsConstructor
public class Approve {
    @Id
    private String id;

    private String borrowingId; //borrowing

    private String cpfOfficial; //borrowing, Official

    private String cpfOfClient; //borrowing, card

    private int quantity; //borrowing

    private Card card; //card

    private String description; //Official

    private String typeApproved; // Type approved: Card, Official, borrowing, Account 

    private Boolean isApproved = false;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public void main(){
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
}
