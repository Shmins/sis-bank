package com.bank.app.entity.administrator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.administrator.model.Administrator;

public interface AdministratorRepository extends MongoRepository<Administrator, String>{
    UserDetails findByCpf(String cpf);
    List<Administrator> findByRg(String rg);
    List<Administrator> findByNameComplete(String nameComplete);
}
