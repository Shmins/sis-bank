package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.model.approve.ApproveAccount;
import com.bank.app.entity.administrator.model.approve.ApproveBorrowing;
import com.bank.app.entity.administrator.model.approve.ApproveCards;
import com.bank.app.entity.administrator.model.approve.ApproveOfficial;
import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.official.model.Official;
import com.bank.app.usecase.agency.AccountDto;
import com.bank.app.usecase.approve.ApproveDto;

import com.bank.app.usecase.approve.ApproveService;

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
    private ApproveService approveService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private OfficialService officialService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> saveApprove(@RequestBody ApproveDto data) {
        try {
            var client = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Approve approve = new Approve(null,
                    data.getBorrowing(),
                    client.getUsername(),
                    data.getAccount(),
                    data.getCard(),
                    data.getDescription(),
                    data.getTypeApproved(),
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now());
            Approve result = this.approveService.createApprove(approve);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            Approve clients = this.approveService.getApproveById(id);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/borrowing/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> getApproveBorrowingAll() {
        try {
            List<ApproveBorrowing> result = this.approveService.getAllBorrowings();
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cards/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> getApproveCardsAll() {
        try {
            List<ApproveCards> result = this.approveService.getAllCards();
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/official/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> getApproveOffcialAll() {
        try {
            List<ApproveOfficial> result = this.approveService.getAllOfficial();
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/account/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getApproveAccountAll() {
        try {
            List<ApproveAccount> result = this.approveService.getAllAccount();
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ApproveDto data) {
        try {
            Approve approve = this.approveService.getApproveById(id);

            approve.setBorrowing(data.getBorrowing() != null ? data.getBorrowing() : approve.getBorrowing());
            approve.setCpfCreatedReq(
                    data.getCpfCreatedReq() != null ? data.getCpfCreatedReq() : approve.getCpfCreatedReq());
            approve.setCard(data.getCard() != null ? data.getCard() : approve.getCard());
            approve.setDescription(data.getDescription() != null ? data.getDescription() : approve.getDescription());
            approve.setIsApproved(data.getIsApproved() != null ? data.getIsApproved() : approve.getIsApproved());
            approve.setTypeApproved(
                    data.getTypeApproved() != null ? data.getTypeApproved() : approve.getTypeApproved());
            approve.setAccount(data.getAccount() != approve.getAccount() ? data.getAccount() : approve.getAccount());

            approve.setUpdateAt(LocalDateTime.now());

            Approve update = this.approveService.updateApprove(approve);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/borrowing/{decision}/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveBorrowing(@PathVariable("decision") Boolean isApproved,
            @PathVariable("id") String id) {
        try {
            Approve approve = this.approveService.getApproveById(id); 
            if (Boolean.TRUE.equals(isApproved)) {
                Borrowing borrowing = approve.getBorrowing();
                Client client = this.clientService.getClientById(borrowing.getCpf());
                borrowing.setIsAuthorized(true);
                this.borrowingService.updateBorrowing(borrowing);
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                true,
                                client.getEmail(),
                                "Resultado do pedido de emprestimo",
                                "borrowing"));

            } else {
                Borrowing borrowing = approve.getBorrowing();
                borrowing.setIsRefused(true);
                this.borrowingService.updateBorrowing(borrowing);
                Client client = this.clientService.getClientById(borrowing.getCpf());
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                false,
                                client.getEmail(),
                                "Resultado do pedido de emprestimo",
                                "borrowing"));

            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (

        Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/cards/{decision}/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveCards(@PathVariable("decision") Boolean isApproved, @PathVariable("id") String id) {
        try {
            Client client = this.clientService.getCardClient(id);
            if (Boolean.TRUE.equals(isApproved)) {
                Card card = client.getCards().stream().filter(res -> id.equals(res.getNumberCard())).toList().get(0);
                List<Card> cards = client.getCards().stream().filter(res -> !id.equals(res.getNumberCard())).toList();
                card.setActive(true);

                cards.add(card);
                client.setCards(cards);

                this.clientService.updateClient(client);
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                true,
                                client.getEmail(),
                                "Resultado do pedido ativação do cartão",
                                "card"));
            } else {
                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                false,
                                client.getEmail(),
                                "Resultado do pedido ativação do cartão",
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

    @PutMapping(value = "/account/{decision}/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> approveAccount(@PathVariable("decision") Boolean decision,
            @PathVariable("id") String id, @RequestBody AccountDto data) {
        try {
            if (Boolean.TRUE.equals(decision) && id != null) {
                Approve approve = this.approveService.getApproveById(id);
                Client client = this.clientService.getClientById(approve.getCpfCreatedReq());
                List<Account> accounts = client.getAccount();
                Account account = new Account(data.getTypeAccount(), data.getNumberAgency(), client.getCpf());
                accounts.add(account);
                client.setAccount(accounts);
                this.clientService.updateClient(client);
                this.approveService.deleteById(id);

                emailService.sendEmailAccount(
                        new EmailAccountDto(
                                client.getNameComplete(),
                                true,
                                client.getEmail(),
                                "Resultado do pedido de abertura de conta",
                                "account"));
            } else {
                Approve approve = this.approveService.getApproveById(id);
                Client client = this.clientService.getClientById(approve.getCpfCreatedReq());
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

    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.approveService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
