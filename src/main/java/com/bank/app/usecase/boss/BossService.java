package com.bank.app.usecase.boss;


import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.boss.model.Boss;

public interface BossService {
    Boss createBoss(Boss boss);
    void insertMany(Iterable<Boss> boss);
    void deleteById(String cpf);
    Boss getBossById(String cpf);
    UserDetails getBossByCpf(String cpf);
    Boss updateBoss(Boss boss);
}