package com.bank.app.usecase.approve;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bank.app.entity.client.model.Account;

@FeignClient("sis-bank-approve")
public interface ApproveClientService {

        @PostMapping(value = "/approve/v1/account/")
        void sendToApproveAccount(@RequestBody Account account,
                        @RequestHeader(value = "Authorization", required = true) String authorizationHeader);

        @PostMapping(value = "/approve/v1/official/")
        void sendToApproveOfficial(@RequestBody String cpf,
                        @RequestHeader(value = "Authorization", required = true) String authorizationHeader);

        @PostMapping(value = "/approve/v1/card/")
        void sendToApproveCard(@RequestBody String numberCard,
                        @RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}