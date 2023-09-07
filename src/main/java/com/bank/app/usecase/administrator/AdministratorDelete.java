package com.bank.app.usecase.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.repository.AdministratorRepository;

@Service
public class AdministratorDelete {
    @Autowired
    private AdministratorRepository administratorRepository;

    public void deleteById(String cpf) {
        this.administratorRepository.deleteById(cpf);
    }
}
