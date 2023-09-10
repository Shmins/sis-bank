package com.bank.app.usecase.approve;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproveDto {
    
    private Borrowing borrowing; //borrowing

    private String cpfCreatedReq; //Official, Adm, Boss

    private Account account;

    private Card card; //card

    private String description; //Official

    private String typeApproved; // Type approved: Card, Official, borrowing, Account 

    private Boolean isApproved = false;
}
