package com.bank.app.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.bank.app.entity.client.model.borrowing.Borrowing;
import com.bank.app.entity.client.repository.BorrowingRepository;

public class BorrowingRepositoryImpl implements BorrowingRepository {

    @Override
    public <S extends Borrowing> S insert(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Borrowing> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'insert'");
    }

    @Override
    public <S extends Borrowing> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com exemplo");
    }

    @Override
    public <S extends Borrowing> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com classificador");
    }

    @Override
    public <S extends Borrowing> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'saveAll'");
    }

    @Override
    public List<Borrowing> findAll() {
        throw new UnsupportedOperationException("Método não encontrado 'findAll'");
    }

    @Override
    public List<Borrowing> findAllById(Iterable<String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'findAllById'");
    }

    @Override
    public <S extends Borrowing> S save(S entity) {
        throw new UnsupportedOperationException("Método não encontrado 'save'");
    }

    @Override
    public Optional<Borrowing> findById(String id) {
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
    public void delete(Borrowing entity) {
        throw new UnsupportedOperationException("Método não encontrado 'delete'");
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAllById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Borrowing> entities) {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Método não encontrado 'deleteAll'");
    }

    @Override
    public List<Borrowing> findAll(Sort sort) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com somente classificador");
    }

    @Override
    public Page<Borrowing> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll' com paginação");
    }

    @Override
    public <S extends Borrowing> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'findOne'");
    }

    @Override
    public <S extends Borrowing> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Método não encontrado 'findAll'");
    }

    @Override
    public <S extends Borrowing> long count(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'count'");
    }

    @Override
    public <S extends Borrowing> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Método não encontrado 'exists'");
    }

    @Override
    public <S extends Borrowing, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Método não encontrado 'findBy'");
    }

    @Override
    public List<Borrowing> findAllByCpf(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByCpf'");
    }
    
}
