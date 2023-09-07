package com.bank.app.usecase.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.administrator.repository.AdministratorRepository;

@Service
public class AdministratorCreate {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator createAdministrator(Administrator adm) {
        return this.administratorRepository.insert(adm);
    }

    public void insertMany(Iterable<Administrator> adm) {
        this.administratorRepository.saveAll(adm);
    }
}
