package com.bank.app.usecase.official;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;


import com.bank.app.entity.official.model.Official;

public interface OfficialService {
    Official createOfficial(Official official);
    void insertMany(Iterable<Official> official);
    void deleteById(String cpf);
    Official getOfficialById(String cpf);
    UserDetails getOfficialByCpf(String cpf);
    List<Official> getAll();
    List<Official> getOfficialByRg(String rg);
    List<Official> getOfficialByNameComplete(String nameComplete);
    Official updateOfficial(Official official);
    Official findByCpfAfterActive(String cpf);
}
