package com.bank.app.infrastructure.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.client.repository.ClientRepository;
import com.bank.app.usecase.client.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        return this.clientRepository.insert(client);
    }

    @Override
    public void insertMany(Iterable<Client> client) {
        this.clientRepository.saveAll(client);
    }

    @Override
    public void deleteById(String cpf) {
        this.clientRepository.deleteById(cpf);
    }

    @Override
    public Client getClientById(String cpf) {
        Optional<Client> client = this.clientRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }

    @Override
    public UserDetails getClientByCpf(String cpf) {
        UserDetails client = this.clientRepository.findByCpf(cpf);
        return client != null ? client : null;
    }

    @Override
    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public List<Client> getClientByEmail(String email) {
        return this.clientRepository.findByEmail(email);
    }

    @Override
    public List<Client> getClientByNameComplete(String nameComplete) {
        return this.clientRepository.findByNameComplete(nameComplete);
    }

    @Override
    public Client getCardClient(String number) {
        return this.clientRepository.findByCard(number);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Account getByIdAccountAfterActive(String id, NumberAgency agency) {
        Client client = this.clientRepository.findByIdAccount(id);
        List<Account> account = client.getAccount();
        for (int i = 0; i < account.stream().count(); i++) {
            if (account.get(i).getId().equals(id)) {
                account.get(i).setIsActive(true);
                account.get(i).setNumberAgency(agency);
                account.get(i).setUpdateAt(LocalDateTime.now());
            }
        }
        client.setAccount(account);

        this.clientRepository.save(client);
        return client.getAccount().stream().filter(res -> res.getId().equals(id)).toList().get(0);
    }

}
