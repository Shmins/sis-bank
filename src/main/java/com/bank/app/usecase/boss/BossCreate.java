package com.bank.app.usecase.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.boss.repository.BossRepository;

@Service
public class BossCreate {
    @Autowired
    private BossRepository bossRepository;

    public Boss createBoss(Boss boss) {
        return this.bossRepository.insert(boss);
    }

    public void insertMany(Iterable<Boss> boss) {
        this.bossRepository.saveAll(boss);
    }
}
