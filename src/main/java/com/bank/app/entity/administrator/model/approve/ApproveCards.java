package com.bank.app.entity.administrator.model.approve;

import java.time.LocalDateTime;

import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveCards {

    private Card card;

    private Boolean isApproved;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
