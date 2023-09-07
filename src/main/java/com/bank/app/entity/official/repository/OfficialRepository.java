package com.bank.app.entity.official.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.official.model.Official;

public interface OfficialRepository extends MongoRepository<Official, String>{
    UserDetails findByCpf(String cpf);
    List<Official> findByRg(String rg);
    List<Official> findByNameComplete(String nameComplete);
}
