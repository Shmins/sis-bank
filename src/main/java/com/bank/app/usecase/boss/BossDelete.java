package com.bank.app.usecase.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.boss.repository.BossRepository;

@Service
public class BossDelete {
    @Autowired
    private BossRepository bossRepository;

    public void deleteById(String cpf) {
        this.bossRepository.deleteById(cpf);
    }
}
