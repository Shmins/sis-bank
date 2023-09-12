package com.bank.app.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;


import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.repository.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public <S extends Account> S insert(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Account> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com exemplo");
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com classificador");
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'saveAll'");
    }

    @Override
    public List<Account> findAll() {
        throw new UnsupportedOperationException("Método não encontrado 'findAll'");
    }

    @Override
    public List<Account> findAllById(Iterable<String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'findAllById'");
    }

    @Override
    public <S extends Account> S save(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'save'");
    }

    @Override
    public Optional<Account> findById(String id) {
        throw new UnsupportedOperationException("Método não encontrado 'findById'");
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
    public void delete(Account entity) {
        throw new UnsupportedOperationException("Método não encontrado 'delete'");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAllById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public List<Account> findAll(Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com somente classificador");
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com paginação");
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findOne'");
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll'");
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'count'");
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'exists'");
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Método não encontrado 'findBy'");
    }

    @Override
    public List<Account> findAllByCpf(String cpf) {
        throw new UnsupportedOperationException("Unimplemented method 'findAllByCpf'");
    }

   
}
