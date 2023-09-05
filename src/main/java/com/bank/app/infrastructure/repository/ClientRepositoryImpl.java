package com.bank.app.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.client.repository.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {
    
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public <S extends Client> S insert(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Client> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com exemplo");
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com classificador");
    }

    @Override
    public <S extends Client> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'saveAll'");
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public List<Client> findAllById(Iterable<String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'findAllById'");
    }

    @Override
    public <S extends Client> S save(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'save'");
    }

    @Override
    public Optional<Client> findById(String id) {
        return this.clientRepository.findById(id);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Método não encontrado 'existsById'");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Método não encontrado 'count'");
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteById'");
    }

    @Override
    public void delete(Client entity) {
        throw new UnsupportedOperationException("Método não encontrado 'delete'");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAllById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Client> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public List<Client> findAll(Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' somente com classificador");
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com paginação");
    }

    @Override
    public <S extends Client> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findOne'");
    }

    @Override
    public <S extends Client> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll'");
    }

    @Override
    public <S extends Client> long count(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'count'");
    }

    @Override
    public <S extends Client> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'exists'");
    }

    @Override
    public <S extends Client, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Método não encontrado 'findBy'");
    }

    @Override
    public UserDetails findByCpf(String cpf) {
        return this.clientRepository.findByCpf(cpf);
    }

    @Override
    public List<Client> findByEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public List<Client> findByCards(List<Card> cards) {
        throw new UnsupportedOperationException("Unimplemented method 'findByCards'");
    }

    @Override
    public List<Client> findByNameComplete(String nameComplete) {
        throw new UnsupportedOperationException("Unimplemented method 'findByNameComplete'");
    }

   

}
