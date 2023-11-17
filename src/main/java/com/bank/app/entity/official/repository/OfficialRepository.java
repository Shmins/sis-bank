package com.bank.app.entity.official.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.official.model.Official;

public interface OfficialRepository extends MongoRepository<Official, String>{
    UserDetails findByCpf(String cpf);
    Official findByRg(String rg);
    Official findByNameComplete(String nameComplete);
}
