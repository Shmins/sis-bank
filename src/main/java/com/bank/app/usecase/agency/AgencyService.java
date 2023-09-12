package com.bank.app.usecase.agency;

import java.util.List;

import com.bank.app.entity.client.model.NumberAgency;

public interface AgencyService {
    NumberAgency createNumberAgency(NumberAgency numberAgency);
    void insertMany(Iterable<NumberAgency> numberAgency);
    void deleteById(String id);
    NumberAgency getNumberAgencyById(String id);
    List<NumberAgency> getAll();
    NumberAgency updateNumberAgency(NumberAgency numberAgency);

}
