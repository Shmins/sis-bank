package com.bank.app.entity.client.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.app.entity.client.model.Account;

public interface AccountRepository extends MongoRepository<Account, String>{
    
}
