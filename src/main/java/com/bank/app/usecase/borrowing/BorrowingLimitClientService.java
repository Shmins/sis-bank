package com.bank.app.usecase.borrowing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bank.app.entity.client.model.BorrowingLimit;

@FeignClient("sis-bank-borrowing")
public interface BorrowingLimitClientService {

    @PostMapping(value = "/borrowing/v1/limit/")
    BorrowingLimit postBorrowingLimit(@RequestBody BorrowingLimit borrowingLimit,
            @RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}