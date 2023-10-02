package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.borrowing.Borrowing;

import com.bank.app.usecase.borrowing.BorrowingService;
import com.bank.app.usecase.borrowing.BorrowingTdo;

@RestController
@RequestMapping("borrowing/v1")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    @Value("${java.hostusers}")
    private String host;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> saveBorrowing(@RequestBody BorrowingTdo data) {
        try {
            Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Borrowing> borrowings = this.borrowingService.getAllForClient(client.getCpf());
            for(Borrowing i : borrowings){
                if(Boolean.FALSE.equals(i.getIsAuthorized()) && Boolean.FALSE.equals(i.getIsRefused())){
                    throw new IllegalArgumentException("Análise de empréstimo em progresso");
                }
            }
            if(data.getQuantity() > client.getBorrowedLimit().getMaxLimit()){
                    throw new IllegalArgumentException("Valor fora do limite do estabelecido");
            }
            Borrowing result = this.borrowingService.createBorrowing(new Borrowing(client.getCpf(), data.getQuantity()));

            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }
    @PostMapping(value = "/approve/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> sendToApprove(@PathVariable("id") String id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Borrowing borrowing = this.borrowingService.getBorrowingById(id);
            borrowing.setIsSendToApprove(true);
            Approve borrowingApprove = new Approve(null, borrowing, id, null, null, null, "borrowing", false, false, LocalDateTime.now(), LocalDateTime.now());
            
            restTemplate.postForLocation(String.format("http:%s/approve/v1/", host), borrowingApprove);
            
            this.borrowingService.updateBorrowing(borrowing);

            return new ResponseEntity<>(borrowing, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }
    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getBorrowingAll() {
        try {
            List<Borrowing> result = this.borrowingService.getAll();

            
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getBorrowingAllForClient() {
        try {
            Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Borrowing> result = this.borrowingService.getAllForClient(client.getCpf());
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> getBorrowing(@PathVariable("id") String id) {
        try {
            Borrowing result = this.borrowingService.getBorrowingById(id);
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateBorrowingById(@PathVariable("id") String id, @RequestBody BorrowingTdo data) {
        try {
            Borrowing borrowing = this.borrowingService.getBorrowingById(id);

            borrowing.setQuantity(
                    data.getQuantity() != borrowing.getQuantity() ? data.getQuantity() : borrowing.getQuantity());
            borrowing.setIsAuthorized(
                    data.getIsAuthorized() != null ? data.getIsAuthorized() : borrowing.getIsAuthorized());
            borrowing.setIsRefused(data.getIsRefused() != null ? data.getIsRefused() : borrowing.getIsRefused());
            borrowing.setUpdateAt(LocalDateTime.now());

            Borrowing update = this.borrowingService.updateBorrowing(borrowing);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteBorrowingById(@PathVariable("id") String id) {
        try {
            this.borrowingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
