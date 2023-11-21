package com.bank.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.client.model.Phone;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.usecase.client.CardDto;
import com.bank.app.usecase.client.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@WebMvcTest(ClientController.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createClient() throws Exception {
    Client client = getClient();
        String result = objectMapper.writeValueAsString(client);
        mockMvc.perform(post("/client/v1/")
                .content(result)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    public Client getClient() {
        Client client = new Client();
        client.setCpf("096.879.823-30");
        client.setNameComplete("Pedro Alyson Teixeira dos Santos");
        client.setEmail("pedro.123@gmail.com");
        client.setPassword("123456789");
        client.setCards(createCards(
                "4321 1246 1151 2772",
                110,
                "credit",
                "Pedro Alyson Teixeira dos Santos",
                "Visa",
                "2025-09-09",
                false));
        client.setAccount(createAccount(
                "c788c60c-544e-4ff0-b603-6af51b5d6ae3",
                new ArrayList<Card>(),
                "chain",
                new NumberAgency(null, null),
                client.getCpf(),
                LocalDateTime.now(),
                LocalDateTime.now()));
        client.setPhone(new Phone());
        client.setAddress(new Address());

        return client;
    }

    private List<Card> createCards(String numberCard, Integer cvc, String typeCard, String nameComplete,
            String typeissuer, String validityDate, Boolean isAuthorized) {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(new CardDto(numberCard, cvc, nameComplete, typeCard, typeissuer, validityDate, false)));
        return cards;
    }

    private List<Account> createAccount(String id, List<Card> card, String typeAccount, NumberAgency numberAgency,
            String cpf, LocalDateTime createAt, LocalDateTime updateAt) {
        List<Account> accounts = new ArrayList<>();

        accounts.add(new Account(
                id.toString(),
                card,
                typeAccount,
                numberAgency,
                cpf,
                createAt,
                updateAt));
        return accounts;
    }
}
