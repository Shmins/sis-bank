package com.bank.app.usecase.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.boss.repository.BossRepository;



@Service
public class BossUpdate {
    @Autowired
    private BossRepository bossRepository;

    public Boss updateBoss(Boss adm){
        return bossRepository.save(adm);
    }
}
