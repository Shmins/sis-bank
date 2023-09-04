package com.bank.app.entity.official.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.app.entity.official.model.Official;

public interface OfficialRepository extends MongoRepository<Official, String>{

}
