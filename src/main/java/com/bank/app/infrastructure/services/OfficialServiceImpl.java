package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.official.model.Official;
import com.bank.app.entity.official.repository.OfficialRepository;
import com.bank.app.usecase.official.OfficialService;

@Service
public class OfficialServiceImpl implements OfficialService {
    @Autowired
    private OfficialRepository officialRepository;

    @Override
    public Official createOfficial(Official official) {
        return this.officialRepository.insert(official);
    }

    @Override
    public void insertMany(Iterable<Official> official) {
        this.officialRepository.saveAll(official);
    }

    @Override
    public void deleteById(String cpf) {
        this.officialRepository.deleteById(cpf);
    }

    @Override
    public Official getOfficialById(String cpf) {
        Optional<Official> official = this.officialRepository.findById(cpf);
        return official.isPresent() ? official.get() : null;
    }

    @Override
    public UserDetails getOfficialByCpf(String cpf) {
        UserDetails official = this.officialRepository.findByCpf(cpf);
        return official != null ? official : null;
    }

    @Override
    public List<Official> getAll() {
        return this.officialRepository.findAll();
    }

    @Override
    public Official getOfficialByRg(String rg) {
        return this.officialRepository.findByRg(rg);
    }

    @Override
    public Official getOfficialByNameComplete(String nameComplete) {
        return this.officialRepository.findByNameComplete(nameComplete);
    }

    @Override
    public Official updateOfficial(Official official) {
        return officialRepository.save(official);
    }

    @Override
    public Official findByCpfAfterActive(String cpf) {
        Official official = this.getOfficialById(cpf);
        official.setIsAuthorized(true);
        return this.officialRepository.save(official);
    }

}
