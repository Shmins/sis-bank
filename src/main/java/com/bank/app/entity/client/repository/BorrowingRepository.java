package com.bank.app.entity.client.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bank.app.entity.client.model.borrowing.Borrowing;

@EnableMongoRepositories
public interface BorrowingRepository extends MongoRepository<Borrowing, String> {
    @Query(value = "{cpf: ?0}")
    List<Borrowing> findAllByCpf(String cpf);
}
