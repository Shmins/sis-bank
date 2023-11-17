package com.bank.app.entity.client.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.model.Client;

public interface ClientRepository extends MongoRepository<Client, String>{
    UserDetails findByCpf(String cpf);
    Client findByNameComplete(String nameComplete);
    
    @Query(value = "{'account.id': ?0}" )
    Client findByAccount(String id);
    @Query(value = "{'cards.numberCard': ?0}" )
    Client findByCard(String number);
}
