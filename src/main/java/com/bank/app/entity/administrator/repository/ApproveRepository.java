package com.bank.app.entity.administrator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.app.entity.administrator.model.approve.Approve;

public interface ApproveRepository extends MongoRepository<Approve, String> {
    
}
