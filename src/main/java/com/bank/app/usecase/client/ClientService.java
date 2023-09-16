package com.bank.app.usecase.client;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.cardmodel.Card;

public interface ClientService {
    Client createClient(Client client);
    void insertMany(Iterable<Client> client);
    void deleteById(String cpf);
    Client getClientById(String cpf);
    UserDetails getClientByCpf(String cpf);
    List<Client> getAll();
    List<Client> getClientByEmail(String email);
    List<Client> getClientByNameComplete(String nameComplete);
    Client getCardClient(String number);
    Client updateClient(Client client);
    Client getByIdAccount(String id);
    Client addCardAccount(Card card, String id);
}