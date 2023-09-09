package com.bank.app.entity.boss.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.boss.model.Boss;

public interface BossRepository extends MongoRepository<Boss, String> {
    UserDetails findByCpf(String cpf);
    List<Boss> findByNameComplete(String nameComplete);
}
