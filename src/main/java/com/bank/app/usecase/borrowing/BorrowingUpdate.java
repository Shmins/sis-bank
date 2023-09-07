package com.bank.app.usecase.borrowing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.repository.BorrowingRepository;

@Service
public class BorrowingUpdate {
    @Autowired
    private BorrowingRepository borrowingRepository;

    public Borrowing updateBorrowing(Borrowing client){
        return borrowingRepository.save(client);
    }
}
