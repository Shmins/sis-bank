package com.bank.app.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.client.model.Phone;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.client.repository.ClientRepository;
import com.bank.app.usecase.client.CardDto;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;
    private Client client;

    @BeforeEach
    void setup() {
        client = new Client();
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
    }

    @Test
    @DisplayName("Get User successfully")
    void findByCpfCase1() {

        this.clientRepository.save(client);

        UserDetails data = this.clientRepository.findByCpf(client.getCpf());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get User failed")
    void findByCpfCase2() {

        UserDetails data = this.clientRepository.findByCpf("000.000.000-00");

        assertThat(data).isNull();
    }

    @Test
    @DisplayName("Get client by name complete successfully")
    void findByNameCompleteCase1() {

        Client data = this.clientRepository.findByNameComplete(client.getNameComplete());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get client by name complete failed")
    void findByNameCompleteCase2() {

        Client data = this.clientRepository.findByNameComplete("break");

        assertThat(data).isNull();
    }

    @Test
    @DisplayName("Get account successfully")
    void findByAccountCase1() {
        String id = client.getAccount().get(0).getId();
        Client data = this.clientRepository.findByAccount(id);

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get client account failed")
    void findByAccountCase2() {

        Client data = this.clientRepository.findByAccount(UUID.randomUUID().toString());

        assertThat(data).isNull();
    }

    @Test
    @DisplayName("Get card successfully")
    void findByCardCase1() {

        Client data = this.clientRepository.findByCard(client.getCards().get(0).getNumberCard());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get card failed")
    void findByCardCase2() {

        Client data = this.clientRepository.findByCard("0000 0000 0000 0000");

        assertThat(data).isNull();
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
