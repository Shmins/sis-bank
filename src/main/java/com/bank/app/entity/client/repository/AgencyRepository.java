package com.bank.app.entity.client.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.app.entity.client.model.NumberAgency;

public interface AgencyRepository extends MongoRepository<NumberAgency, String>{
    
}
