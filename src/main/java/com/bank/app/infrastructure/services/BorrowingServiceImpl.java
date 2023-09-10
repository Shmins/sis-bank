package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.repository.BorrowingRepository;
import com.bank.app.usecase.borrowing.BorrowingService;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Override
    public Borrowing createBorrowing(Borrowing borrowing) {
        return this.borrowingRepository.insert(borrowing);
    }

    @Override
    public void insertMany(Iterable<Borrowing> borrowing) {
        this.borrowingRepository.saveAll(borrowing);
    }

    @Override
    public void deleteById(String cpf) {
        this.borrowingRepository.deleteById(cpf);
    }

    @Override
    public Borrowing getBorrowingById(String id) {
        Optional<Borrowing> borrowing = this.borrowingRepository.findById(id);
        return borrowing.isPresent() ? borrowing.get() : null;
    }

    @Override
    public List<Borrowing> getAllForClient(String cpf){
        return this.borrowingRepository.findAllByCpf(cpf);
   }

    @Override
    public List<Borrowing> getAll() {
        return this.borrowingRepository.findAll();
    }

    @Override
    public Borrowing updateBorrowing(Borrowing client){
        return borrowingRepository.save(client);
    }
    
}
