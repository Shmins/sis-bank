package com.bank.app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.client.model.Phone;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.client.repository.ClientRepository;
import com.bank.app.infrastructure.services.ClientServiceImpl;
import com.bank.app.usecase.client.CardDto;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ClientServiceTest {
    @Mock
    private ClientRepository clientService;
    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    private Client client;
    @BeforeEach
    void setup(){
        client =  new Client();
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
    @DisplayName("Create client - service client")
    void createClientCase1() {
        when(clientService.save(Mockito.any(Client.class))).thenReturn(client);

        Client result = clientServiceImpl.createClient(client);
        
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Get client by cpf and return UserDetails successfully - service client")
    void getClientByCpfCase1() {
        
        when(clientService.findByCpf(client.getCpf())).thenReturn(client);
        UserDetails user = clientServiceImpl.getClientByCpf(client.getCpf());
        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by cpf and return UserDetails failed - service client")
    void getClientByCpfCase2(){

        UserDetails user = clientServiceImpl.getClientByCpf("000.000.000-00");

        assertThat(user).isNull();
    }
    @Test
    @DisplayName("Get client by cpf successfully - service client")
    void getClientByIdCase1() {

        Optional<Client> cl = Optional.of(client);

        when(clientService.findById(client.getCpf())).thenReturn(cl);

        Client user = clientServiceImpl.getClientById(client.getCpf());
        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by cpf failed - service client")
    void getClientByIdCase2(){

        Client user = clientServiceImpl.getClientById("000.000.000-00");

        assertThat(user).isNull();
    }
    @Test
    @DisplayName("Get client by name complete successfully - service client")
    void getClientByNameCompleteCase1() {
        
        when(clientService.findByNameComplete(client.getNameComplete())).thenReturn(client);
        Client user = clientServiceImpl.getClientByNameComplete(client.getNameComplete());
        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by name complete failed - service client")
    void getClientByNameCompleteCase2(){

        Client user = clientServiceImpl.getClientByNameComplete("");

        assertThat(user).isNull();
    }
    @Test
    @DisplayName("Get client by number card successfully - service client")
    void getCardClientCase1() {
        
        when(clientService.findByCard(client.getCards().get(0).getNumberCard())).thenReturn(client);

        Client user = clientServiceImpl.getCardClient(client.getCards().get(0).getNumberCard());

        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by number card failed - service client")
    void getCardClientCase2(){

        Client user = clientServiceImpl.getCardClient("");

        assertThat(user).isNull();
    }
    @Test
    @DisplayName("Get update client successfully - service client")
    void updateClientCase1() {
        client.setEmail("teste.@gmail.com");

        when(clientServiceImpl.updateClient(client)).thenReturn(client);

        Client user = clientServiceImpl.updateClient(client);
        
        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by number card successfully - service client")
    void getByIdAccountCase1() {
        
        when(clientService.findByAccount(client.getAccount().get(0).getId())).thenReturn(client);

        Client user = clientServiceImpl.getByIdAccount(client.getAccount().get(0).getId());

        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by number card failed - service client")
    void getByIdAccountCase2(){

        Client user = clientServiceImpl.getByIdAccount("");

        assertThat(user).isNull();
    }
    @Test
    @DisplayName("Get client by number card successfully - service client")
    void addCardAccountCase1() {

        Card card = new Card("4321 1246 1151 2772",
                110,
                "credit",
                "Pedro Alyson Teixeira dos Santos",
                "Visa",
                "2025-09-09",
                false);
        when(clientService.findByAccount(client.getAccount().get(0).getId())).thenReturn(client);

        when(clientService.save(client)).thenReturn(client);

        Client user = clientServiceImpl.addCardAccount(card, client.getAccount().get(0).getId());

        assertThat(user).isNotNull();
    }
    @Test
    @DisplayName("Get client by number card failed - service client")
    void addCardAccountCase2(){

        Client user = clientServiceImpl.getByIdAccount("");

        assertThat(user).isNull();
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
