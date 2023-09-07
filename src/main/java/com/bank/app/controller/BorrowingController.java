package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.usecase.borrowing.BorrowingCreate;
import com.bank.app.usecase.borrowing.BorrowingDelete;
import com.bank.app.usecase.borrowing.BorrowingSearch;
import com.bank.app.usecase.borrowing.BorrowingTdo;
import com.bank.app.usecase.borrowing.BorrowingUpdate;


@RestController
@RequestMapping("client/v1/borrowing")
@CrossOrigin(origins = "*")
public class BorrowingController {
    @Autowired
    private BorrowingSearch borrowingSearch;
    @Autowired
    private BorrowingCreate borrowingCreate;
    @Autowired
    private BorrowingUpdate borrowingUpdate;
    @Autowired
    private BorrowingDelete borrowingDelete;

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> postBorrowing(@RequestBody BorrowingTdo data) {
        try {
            Borrowing borrowing = new Borrowing(data.getCpf(), data.getQuantity());
            Borrowing result = this.borrowingCreate.createBorrowing(borrowing);

            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getBorrowingAll() {
        try {
            List<Borrowing> result = this.borrowingSearch.getAll();
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<?> getBorrowing(@PathVariable("cpf") String cpf) {
        try {
            Borrowing result = this.borrowingSearch.getBorrowingById(cpf);
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/{cpf}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("cpf") String id, @RequestBody BorrowingTdo data) {
        try {
            Borrowing borrowing = this.borrowingSearch.getBorrowingById(id);

            borrowing.setCpf(data.getCpf() != null ? data.getCpf() : borrowing.getCpf());
            borrowing.setQuantity(
                    data.getQuantity() != borrowing.getQuantity() ? data.getQuantity() : borrowing.getQuantity());
            borrowing.setIsAuthorized(
                    data.getIsAuthorized() != null ? data.getIsAuthorized() : borrowing.getIsAuthorized());
            borrowing.setIsRefused(data.getIsRefused() != null ? data.getIsRefused() : borrowing.getIsRefused());
            borrowing.setUpdateAt(LocalDateTime.now());

            Borrowing update = this.borrowingUpdate.updateBorrowing(borrowing);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<?> deleteById(@PathVariable("cpf") String cpf) {
        try {
            this.borrowingDelete.deleteById(cpf);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
