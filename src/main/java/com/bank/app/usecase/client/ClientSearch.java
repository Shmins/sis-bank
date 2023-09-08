package com.bank.app.usecase.client;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.repository.ClientRepository;

@Service
public class ClientSearch {
    @Autowired
    private ClientRepository clientRepository;

    public Client getClientById(String cpf) {
        Optional<Client> client = this.clientRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }
    public UserDetails getClientByCpf(String cpf) {
       UserDetails client = this.clientRepository.findByCpf(cpf);
        return client != null ? client : null;
    }
    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }

    public List<Client> getClientByEmail(String email) {
        return this.clientRepository.findByEmail(email);
    }
    public List<Client> getClientByNameComplete(String nameComplete){
        return this.clientRepository.findByNameComplete(nameComplete);
    }
     public Client getCardClient(String number) {
        return this.clientRepository.findByCard(number);
    }
}
