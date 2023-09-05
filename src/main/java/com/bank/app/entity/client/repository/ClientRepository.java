package com.bank.app.entity.client.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.model.Client;
import java.util.List;
import com.bank.app.entity.client.model.cardmodel.Card;


public interface ClientRepository extends MongoRepository<Client, String>{
    UserDetails findByCpf(String cpf);
    List<Client> findByEmail(String email);
    List<Client> findByCards(List<Card> cards);
    List<Client> findByNameComplete(String nameComplete);
    @Query(value = "{'cards.numberCard': ?0}" )
    Client findByCard(String number);
}
