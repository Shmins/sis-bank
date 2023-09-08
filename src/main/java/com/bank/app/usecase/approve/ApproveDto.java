package com.bank.app.usecase.approve;

import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproveDto {
    private String borrowingId; //borrowing

    private String cpfOfficial; //borrowing, Official

    private String cpfOfClient; //borrowing, card

    private int quantity; //borrowing

    private Card card; //card

    private String description; //Official

    private String typeApproved; // Type approved: Card, Official, borrowing, Account 

    private Boolean isApproved = false;
}
