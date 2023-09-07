package com.bank.app.usecase.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.repository.ClientRepository;

@Service
public class ClientDelete {
    @Autowired
    private ClientRepository clientRepository;

    public void deleteById(String cpf){
        this.clientRepository.deleteById(cpf);
    }
}
