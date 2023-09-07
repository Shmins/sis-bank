package com.bank.app.usecase.official;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.official.model.Official;
import com.bank.app.entity.official.repository.OfficialRepository;


@Service
public class OfficialUpdate {
    @Autowired
    private OfficialRepository officialRepository;

    public Official updateOfficial(Official adm){
        return officialRepository.save(adm);
    }
}
