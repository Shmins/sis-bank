package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.client.repository.AgencyRepository;
import com.bank.app.usecase.agency.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;
    @Override
    public NumberAgency createNumberAgency(NumberAgency numberAgency) {
        return this.agencyRepository.insert(numberAgency);
    }

    @Override
    public void insertMany(Iterable<NumberAgency> numberAgency) {
        this.agencyRepository.saveAll(numberAgency);
    }

    @Override
    public void deleteById(String id) {
        this.agencyRepository.deleteById(id);
    }

    @Override
    public NumberAgency getNumberAgencyById(String id) {
        Optional<NumberAgency> client = this.agencyRepository.findById(id);
        return client.isPresent() ? client.get() : null;
    }

    @Override
    public List<NumberAgency> getAll() {
        return this.agencyRepository.findAll();
    }

    @Override
    public NumberAgency updateNumberAgency(NumberAgency NumberAgency) {
        return agencyRepository.save(NumberAgency);
    }
   
}
