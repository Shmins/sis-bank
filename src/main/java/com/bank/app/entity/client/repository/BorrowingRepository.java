package com.bank.app.entity.client.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bank.app.entity.client.model.borrowing.Borrowing;

@EnableMongoRepositories
public interface BorrowingRepository extends MongoRepository<Borrowing, String> {

}
