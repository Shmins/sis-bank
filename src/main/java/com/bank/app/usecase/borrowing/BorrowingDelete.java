package com.bank.app.usecase.borrowing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.repository.BorrowingRepository;

@Service
public class BorrowingDelete {
    @Autowired
    private BorrowingRepository borrowingRepository;

    public void deleteById(String cpf) {
        this.borrowingRepository.deleteById(cpf);
    }
}
