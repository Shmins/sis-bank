package com.bank.app.usecase.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.repository.ClientRepository;

@Service
public class ClientUpdate {
    @Autowired
    private ClientRepository clientRepository;

    public Client updateClient(Client client){
        return clientRepository.save(client);
    }
}
