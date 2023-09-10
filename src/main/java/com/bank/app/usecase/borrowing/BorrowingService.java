package com.bank.app.usecase.borrowing;

import java.util.List;


import com.bank.app.entity.client.model.borrowing.Borrowing;

public interface BorrowingService {
    Borrowing createBorrowing(Borrowing borrowing);
    void insertMany(Iterable<Borrowing> borrowing);
    void deleteById(String cpf);
    Borrowing getBorrowingById(String id);
    List<Borrowing> getAllForClient(String cpf);
    List<Borrowing> getAll();
    Borrowing updateBorrowing(Borrowing borrowing);
}