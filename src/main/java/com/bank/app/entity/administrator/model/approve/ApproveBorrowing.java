package com.bank.app.entity.administrator.model.approve;

import java.time.LocalDateTime;

import com.bank.app.entity.client.model.borrowing.Borrowing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveBorrowing {
    private Borrowing borrowing;

    private Boolean isApproved;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
