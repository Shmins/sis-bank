package com.bank.app.usecase.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.administrator.repository.AdministratorRepository;


@Service
public class AdministratorUpdate {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator updateAdministrator(Administrator adm){
        return administratorRepository.save(adm);
    }
}
