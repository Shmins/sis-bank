package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;

import com.bank.app.entity.client.model.cardmodel.Card;

import com.bank.app.usecase.agency.AccountDto;
import com.bank.app.usecase.approve.ApproveService;
import com.bank.app.usecase.client.CardDto;
import com.bank.app.usecase.client.ClientDto;
import com.bank.app.usecase.client.ClientService;

import jakarta.annotation.security.RolesAllowed;

@Controller
@RestController
@RequestMapping("client/v1")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ApproveService approveService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> saveClient(@RequestBody ClientDto data) {
        try {
            String code = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(code);
            Client client = new Client(
                    data.getCpf(),
                    data.getNameComplete(),
                    data.getEmail(),
                    data.getPassword(),
                    data.getAccount(),
                    data.getPhone(),
                    data.getAddress());
            Client result = this.clientService.createClient(client);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }
    @RolesAllowed("CLIENT")
    @PostMapping(value = "/approve/account", produces = "application/json")
    public ResponseEntity<?> approveAccount(@RequestBody AccountDto data) {
        try {

            Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = new Account(data.getTypeAccount(), data.getNumberAgency(), client.getCpf());

            Approve result = this.approveService.createApprove(
                    new Approve(null,
                            null,
                            client.getCpf(),
                            account,
                            null,
                            null,
                            "account",
                            false,
                            null,
                            null));
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }
    
    @PostMapping(value = "/account/{cpf}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto data, @PathVariable("cpf") String cpf) {
        try {
            Client client = this.clientService.getClientById(cpf);
            if(client.getAccount().size() == client.getAccountLimit()){
                throw new IllegalArgumentException("Máximo de contas alcançado");
            }
            Account account = new Account(data.getTypeAccount(), data.getNumberAgency(), client.getCpf());
            List<Account> ac = client.getAccount();
            ac.add(account);
            client.setAccount(ac);
            Client result = this.clientService.updateClient(client);
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PostMapping(value = "/cards/add", produces = "application/json")
    @RolesAllowed("CLIENT")
    public ResponseEntity<?> saveCards(@RequestBody Card data) {
        try {
            Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (client.getCards().size() == 6) {
                throw new GenericException("limite de cartões exedido");
            }
            List<Card> cards = client.getCards().isEmpty() ? new ArrayList<>() : client.getCards();
            for (Card i : cards) {
                if (i.getNumberCard().equals(data.getNumberCard()) || i.getCvc() == data.getCvc()) {
                    throw new IllegalArgumentException("Número de cartão ou CVC duplicado");
                }
            }
            cards.add(data);

            client.setCards(cards);
            Client update = this.clientService.updateClient(client);
            if (update != null && data.isActive()) {
                this.approveService.createApprove(
                        new Approve(null,
                                null,
                                client.getCpf(),
                                null,
                                data,
                                null,
                                "cards",
                                false,
                                null,
                                null));
            }
            return new ResponseEntity<>(update, HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }
    @PostMapping(value = "/account/cards/{id}", produces = "application/json")
    @RolesAllowed("CLIENT")
    public ResponseEntity<?> saveCardsAccount(@PathVariable("id") String id, @RequestBody Card data) {
        try {
            Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           
            List<Account> accounts = client.getAccount();
            Account account = accounts.stream().filter(res -> res.getId().equals(id)).toList().get(0);
            List<Card> cards =  account.getCards();
             if (cards.size() == 2) {
                throw new GenericException("limite de cartões exedido");
            }
            for (Card i : cards) {
                if (i.getNumberCard().equals(data.getNumberCard()) || i.getCvc() == data.getCvc()) {
                    throw new IllegalArgumentException("Número de cartão ou CVC duplicado");
                }
            }

            cards.add(data);
            accounts.remove(account);
            account.setCards(cards);
            accounts.add(account);
            client.setAccount(accounts);
            Client update = this.clientService.updateClient(client);
            if (update != null && data.isActive()) {
                this.approveService.createApprove(
                        new Approve(null,
                                null,
                                client.getCpf(),
                                null,
                                data,
                                null,
                                "cards",
                                false,
                                null,
                                null));
            }
            return new ResponseEntity<>(update, HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getAll() {
        List<Client> clients = this.clientService.getAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/cards/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getAllCards() {
        List<Client> clients = this.clientService.getAll();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).getCards().forEach(cards::add);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping(value = "/accounts/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getAllAccounts() {
        List<Client> clients = this.clientService.getAll();
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).getAccount().forEach(accounts::add);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/cards/cpf/{cpf}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> getAllCardsByCpf(@PathVariable("cpf") String cpf) {
        try {
            Client clients = this.clientService.getClientById(cpf);

            return new ResponseEntity<>(clients.getCards(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cards/{number}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getCardByNumberCard(@PathVariable("number") String number) {
        try {
            Client client = this.clientService.getCardClient(number);
            var card = client.getCards().stream().filter(res -> number.equals(res.getNumberCard()));
            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cpf/{cpf}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> getById(@PathVariable("cpf") String cpf) {
        try {
            Client clients = this.clientService.getClientById(cpf);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        try {
            List<Client> clients = this.clientService.getClientByEmail(email);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/email/{nameComplete}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getByNameComplete(@PathVariable("nameComplete") String nameComplete) {
        try {
            List<Client> clients = this.clientService.getClientByNameComplete(nameComplete);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/cards/{number}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> updateCardById(@PathVariable("number") String number, @RequestBody CardDto data) {
        try {
            Client client = this.clientService.getCardClient(number);
            List<Card> cards = client.getCards();

            Card card = cards.stream().filter(res -> res.getNumberCard().equals(number)).toList().get(0);
            cards.remove(card);
            card.setNameComplete(data.nameComplete() != null ? data.nameComplete() : card.getNameComplete());
            card.setValidityDate(data.validityDate() != null ? data.validityDate() : card.getValidityDate());
            card.setTypeCard(data.typeCard() != null ? data.typeCard() : card.getTypeCard());
            card.setUpdateAt(LocalDateTime.now());
            cards.add(card);
            client.setCards(cards);
            this.clientService.updateClient(client);
            return new ResponseEntity<>(cards, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ClientDto data) {
        try {
            Client client = this.clientService.getClientById(id);

            client.setNameComplete(data.getNameComplete() != null ? data.getNameComplete() : client.getNameComplete());
            client.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : client.getPassword());
            client.setEmail(data.getEmail() != null ? data.getEmail() : client.getEmail());
            client.setPhone(data.getPhone() != null ? data.getPhone() : client.getPhone());
            client.setAddress(data.getAddress() != null ? data.getAddress() : client.getAddress());
            client.setAccountLimit(data.getAccountLimit() != null? data.getAccountLimit(): client.getAccountLimit());
            client.setUpdateAt(LocalDateTime.now());

            Client update = this.clientService.updateClient(client);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed("CLIENT")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.clientService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/cards/{number}")
    @RolesAllowed("CLIENT")
    public ResponseEntity<?> deleteCardById(@PathVariable("number") String number) {
        try {
            Client client = this.clientService.getCardClient(number);
            List<Card> card = client.getCards().stream().filter(res -> !number.equals(res.getNumberCard())).toList();
            client.setCards(card);
            this.clientService.updateClient(client);
            return new ResponseEntity<>(card, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
