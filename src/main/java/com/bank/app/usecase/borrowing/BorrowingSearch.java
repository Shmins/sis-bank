package com.bank.app.usecase.borrowing;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.repository.BorrowingRepository;

@Service
public class BorrowingSearch {
    @Autowired
    private BorrowingRepository borrowingRepository;

    public Borrowing getBorrowingById(String cpf) {
        Optional<Borrowing> borrowing = this.borrowingRepository.findById(cpf);
        return borrowing.isPresent() ? borrowing.get() : null;
    }
    public List<Borrowing> getAllForClient(String cpf){
         return this.borrowingRepository.findAllByCpf(cpf);
    }
    public List<Borrowing> getAll() {
        return this.borrowingRepository.findAll();
    }
}
