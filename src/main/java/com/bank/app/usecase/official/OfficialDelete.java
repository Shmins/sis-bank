package com.bank.app.usecase.official;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.official.repository.OfficialRepository;

@Service
public class OfficialDelete {
    @Autowired
    private OfficialRepository officialRepository;

    public void deleteById(String cpf) {
        this.officialRepository.deleteById(cpf);
    }
}
