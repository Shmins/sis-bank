package com.bank.app.usecase.official;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.official.model.Official;
import com.bank.app.entity.official.repository.OfficialRepository;

@Service
public class OfficialSearch {
    @Autowired
    private OfficialRepository officialRepository;

    public Official getOfficialById(String cpf) {
        Optional<Official> client = this.officialRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }

    public List<Official> getAll() {
        return this.officialRepository.findAll();
    }

    public List<Official> getOfficialByRg(String rg) {
        return this.officialRepository.findByRg(rg);
    }
    public List<Official> getOfficialByNameComplete(String nameComplete){
        return this.officialRepository.findByNameComplete(nameComplete);
    }
}
