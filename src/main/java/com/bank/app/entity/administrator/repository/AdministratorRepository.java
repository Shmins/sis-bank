package com.bank.app.entity.administrator.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.administrator.model.Administrator;

public interface AdministratorRepository extends MongoRepository<Administrator, String>{
    UserDetails findByCpf(String cpf);
    Administrator findByRg(String rg);
    Administrator findByNameComplete(String nameComplete);
}
