package com.bank.app.entity.client.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bank.app.entity.client.model.cardmodel.Card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String id;

    private List<Card> cards = new ArrayList<>();

    private String typeAccount;

    private NumberAgency numberAgency;

    private String cpf;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
