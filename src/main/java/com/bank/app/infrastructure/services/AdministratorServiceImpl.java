package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.administrator.repository.AdministratorRepository;

import com.bank.app.usecase.administrator.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator createAdministrator(Administrator adm) {
        return this.administratorRepository.insert(adm);
    }

    @Override
    public void insertMany(Iterable<Administrator> adm) {
        this.administratorRepository.saveAll(adm);
    }

    @Override
    public void deleteById(String cpf) {
        this.administratorRepository.deleteById(cpf);
    }

    @Override
    public Administrator getAdmById(String cpf) {
        Optional<Administrator> client = this.administratorRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }

    @Override
    public UserDetails getAdmByCpf(String cpf) {
        UserDetails adm = this.administratorRepository.findByCpf(cpf);
        return adm != null ? adm : null;
    }

    @Override
    public List<Administrator> getAll() {
        return this.administratorRepository.findAll();
    }

    @Override
    public List<Administrator> getAdmByRg(String rg) {
        return this.administratorRepository.findByRg(rg);
    }

    @Override
    public List<Administrator> getAdmByNameComplete(String nameComplete) {
        return this.administratorRepository.findByNameComplete(nameComplete);
    }

    @Override
    public Administrator updateAdministrator(Administrator adm) {
        return administratorRepository.save(adm);
    }

}
