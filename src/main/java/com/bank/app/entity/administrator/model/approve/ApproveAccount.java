package com.bank.app.entity.administrator.model.approve;

import java.time.LocalDateTime;

import com.bank.app.entity.client.model.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproveAccount {
    private String id;
    
    private Account account;

    private Boolean isApproved;

    private Boolean isRefused;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
