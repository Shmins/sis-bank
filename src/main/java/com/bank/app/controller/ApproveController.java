package com.bank.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.BorrowedLimit;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.official.model.Official;

import com.bank.app.usecase.borrowing.BorrowingService;
import com.bank.app.usecase.client.ClientService;
import com.bank.app.usecase.email.EmailAccountDto;
import com.bank.app.usecase.email.EmailService;
import com.bank.app.usecase.official.OfficialService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("approve/v1")
public class ApproveController {
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private OfficialService officialService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;

    @PutMapping(value = "/borrowing/{decision}/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveBorrowing(@PathVariable("decision") Boolean isApproved,
            @RequestBody Borrowing data) {
        try {
            if (Boolean.TRUE.equals(isApproved)) {
                Client client = this.clientService.getClientById(data.getCpf());
                this.borrowingService.updateBorrowing(data);

                client.setBorrowedLimit(
                        new BorrowedLimit(0, (client.getBorrowedLimit().getMaxLimit() - data.getQuantity())));
                this.clientService.updateClient(client);

                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                true,
                                client.getEmail(),
                                "Resultado do pedido de empréstimo",
                                "borrowing"));

            } else {
                this.borrowingService.updateBorrowing(data);
                Client client = this.clientService.getClientById(data.getCpf());
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                false,
                                client.getEmail(),
                                "Resultado do pedido de empréstimo",
                                "borrowing"));
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (

        Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/cards/{decision}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveCards(@PathVariable("decision") Boolean isApproved, @RequestBody Card data) {
        try {
            if(Boolean.TRUE.equals(isApproved)){
                Client client = this.clientService.getCardClient(data.getNumberCard());
                    for (Card card : client.getCards()) {
                        if (card.getNumberCard().equals(data.getNumberCard())) {
                            card.setActive(true);
                        }
                    }

                    this.clientService.updateClient(client);
                    emailService.sendEmailAccount(
                            new EmailAccountDto(
                                    client.getNameComplete(),
                                    true,
                                    client.getEmail(),
                                    "Resultado do pedido de ativação do cartão",
                                    "card"));
            } else {
                Client client = this.clientService.getCardClient(data.getNumberCard());
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                false,
                                client.getEmail(),
                                "Resultado do pedido de ativação do cartão",
                                "card"));
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/official/{decision}/{cpf}")
    @RolesAllowed("BOSS")
    public ResponseEntity<?> approveOfficial(@PathVariable("decision") Boolean isApproved,
            @PathVariable("cpf") String cpf) {
        try {
            if (Boolean.TRUE.equals(isApproved)) {
                Official official = this.officialService.findByCpfAfterActive(cpf);

                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                official.getNameComplete(),
                                true,
                                official.getEmail(),
                                "Resultado do pedido de aprovação de função",
                                "official"));
            } else {
                Official official = this.officialService.getOfficialById(cpf);
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                official.getNameComplete(),
                                false,
                                official.getEmail(),
                                "Resultado do pedido de aprovação de função",
                                "official"));
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/account/{decision}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveAccount(@PathVariable("decision") Boolean decision, @RequestBody Account data) {
        try {
            if (Boolean.TRUE.equals(decision)) {
                Client client = this.clientService.getClientById(data.getCpf());
                List<Account> accounts = client.getAccount();
                accounts.add(data);
                client.setAccount(accounts);
                this.clientService.updateClient(client);

                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                true,
                                client.getEmail(),
                                "Resultado do pedido de abertura de conta",
                                "account"));
            } else {
                Client client = this.clientService.getClientById(data.getCpf());
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                false,
                                client.getEmail(),
                                "Resultado do pedido de abertura de conta",
                                "account"));

            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
