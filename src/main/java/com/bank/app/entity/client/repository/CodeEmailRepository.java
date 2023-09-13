package com.bank.app.entity.client.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.app.entity.client.model.email.EmailCode;

public interface CodeEmailRepository extends MongoRepository<EmailCode, String>{
    
}
