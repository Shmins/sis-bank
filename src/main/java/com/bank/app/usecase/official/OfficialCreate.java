package com.bank.app.usecase.official;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.official.model.Official;
import com.bank.app.entity.official.repository.OfficialRepository;

@Service
public class OfficialCreate {
    @Autowired
    private OfficialRepository officialRepository;

    public Official createOfficial(Official official) {
        return this.officialRepository.insert(official);
    }

    public void insertMany(Iterable<Official> official) {
        this.officialRepository.saveAll(official);
    }
}
