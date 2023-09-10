package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.boss.repository.BossRepository;
import com.bank.app.usecase.boss.BossService;


@Service
public class BossServiceImpl implements BossService {
    @Autowired
    private BossRepository bossRepository;

    @Override
    public Boss createBoss(Boss boss) {
        return this.bossRepository.insert(boss);
    }

    @Override
    public void insertMany(Iterable<Boss> boss) {
        this.bossRepository.saveAll(boss);
    }
    @Override
   public void deleteById(String cpf) {
        this.bossRepository.deleteById(cpf);
    }

    @Override
    public Boss getBossById(String cpf) {
        Optional<Boss> client = this.bossRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }

    @Override
    public UserDetails getBossByCpf(String cpf) {
        UserDetails client = this.bossRepository.findByCpf(cpf);
        return client != null ? client : null;
    }

    @Override
    public List<Boss> getBossByNameComplete(String nameComplete){
        return this.bossRepository.findByNameComplete(nameComplete);
    }

    @Override
   public Boss updateBoss(Boss adm){
        return bossRepository.save(adm);
    }

}
