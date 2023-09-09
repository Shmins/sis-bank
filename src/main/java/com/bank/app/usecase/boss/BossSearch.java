package com.bank.app.usecase.boss;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.boss.repository.BossRepository;

@Service
public class BossSearch {
    @Autowired
    private BossRepository bossRepository;

    public Boss getBossById(String cpf) {
        Optional<Boss> client = this.bossRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }
    public UserDetails getBossByCpf(String cpf) {
       UserDetails client = this.bossRepository.findByCpf(cpf);
        return client != null ? client : null;
    }
    public List<Boss> getAll() {
        return this.bossRepository.findAll();
    }
    public List<Boss> getOfficialByNameComplete(String nameComplete){
        return this.bossRepository.findByNameComplete(nameComplete);
    }
}
