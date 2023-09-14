package com.bank.app.entity.administrator.model.approve;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveOfficial {

    private String cpfCreatedReq;

    private Boolean isApproved;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
