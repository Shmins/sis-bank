package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.client.repository.ClientRepository;
import com.bank.app.usecase.client.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        return this.clientRepository.save(client);
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
    public Client getClientByNameComplete(String nameComplete) {
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
    public Client getByIdAccount(String id) {
        return this.clientRepository.findByAccount(id);
    }

    @Override
    public Client addCardAccount(Card card, String id) {
        Client client = this.clientRepository.findByAccount(id);
        List<Account> accounts = client.getAccount();
        for(Account i : accounts){
            if(i.getId().equals(id)){
                var cards = i.getCards();
                cards.add(card);
                i.setCards(cards);
            }
        }
        return this.clientRepository.save(client);
    }

}
