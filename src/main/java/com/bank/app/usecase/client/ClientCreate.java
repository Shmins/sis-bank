package com.bank.app.usecase.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.repository.ClientRepository;
@Service
public class ClientCreate {
    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return this.clientRepository.insert(client);
    }

    public void insertMany(Iterable<Client> client) {
        this.clientRepository.saveAll(client);
    }
}
