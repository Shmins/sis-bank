package com.bank.app.usecase.borrowing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.repository.BorrowingRepository;

@Service
public class BorrowingCreate {
    @Autowired
    private BorrowingRepository borrowingRepository;

    public Borrowing createBorrowing(Borrowing borrowing) {
        return this.borrowingRepository.insert(borrowing);
    }

    public void insertMany(Iterable<Borrowing> borrowing) {
        this.borrowingRepository.saveAll(borrowing);
    }
}
