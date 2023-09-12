package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.official.model.Official;
import com.bank.app.usecase.account.AccountService;
import com.bank.app.usecase.approve.ApproveDto;

import com.bank.app.usecase.approve.ApproveService;

import com.bank.app.usecase.borrowing.BorrowingService;
import com.bank.app.usecase.client.ClientService;
import com.bank.app.usecase.official.OfficialService;

@RestController
@RequestMapping("approve/v1")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
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
    private AccountService accountService;
    @PostMapping(value = "", produces = "application/json")
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            Approve clients = this.approveService.getApproveById(id);
    
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }
    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getApprovegAll() {
        try {
           var result = this.approveService.getAll();
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

    @PutMapping(value = "/{router}/{id}")
    public ResponseEntity<?> getById(@PathVariable("router") String router, @PathVariable("id") String id) {
        try {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            switch (router) {
                case "borrowing": {
                    Borrowing borrowing = this.borrowingService.getBorrowingById(id);
                    borrowing.setIsAuthorized(true);
                    this.borrowingService.updateBorrowing(borrowing);
                    return new ResponseEntity<>(borrowing, HttpStatus.OK);
                }
                case "official": {
                    var role = user.getAuthorities().stream().toList();
                    if(!role.get(0).getAuthority().equals("ROLE_BOSS")){
                        throw new IllegalAccessError("Não autorizado");
                    }
                    Official official = this.officialService.findByCpfAfterActive(id);

                    return new ResponseEntity<>(official, HttpStatus.OK);
                }
                case "cards": {
                    Client client = this.clientService.getCardClient(id);
                    Card card = client.getCards().stream().filter(res -> id.equals(res.getNumberCard())).toList().get(0);
                    List<Card> cards = client.getCards().stream().filter(res -> !id.equals(res.getNumberCard())).toList();
                    card.setActive(true);

                    cards.add(card);
                    client.setCards(cards);

                    this.clientService.updateClient(client);
                    
                    return new ResponseEntity<>(HttpStatus.OK);

                }
                case "account": {
                    Account account = this.accountService.findByIdAfterActive(id);
                    return new ResponseEntity<>(account, HttpStatus.OK);

                }
                default: {
                   return null;
                }
            }

           
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

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
