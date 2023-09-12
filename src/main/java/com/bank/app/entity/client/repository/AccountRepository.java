package com.bank.app.entity.client.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bank.app.entity.client.model.Account;

public interface AccountRepository extends MongoRepository<Account, String>{
    @Query(value = "{cpf: ?0}")
    List<Account> findAllByCpf(String cpf);
    
}
