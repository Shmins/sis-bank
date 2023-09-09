package com.bank.app.usecase.administrator;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.administrator.repository.AdministratorRepository;

@Service
public class AdministratorSearch {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator getClientById(String cpf) {
        Optional<Administrator> client = this.administratorRepository.findById(cpf);
        return client.isPresent() ? client.get() : null;
    }
    public UserDetails getAdmByCpf(String cpf) {
       UserDetails adm = this.administratorRepository.findByCpf(cpf);
        return adm != null ? adm : null;
    }
    public List<Administrator> getAll() {
        return this.administratorRepository.findAll();
    }

    public List<Administrator> getAdmByRg(String rg) {
        return this.administratorRepository.findByRg(rg);
    }
    public List<Administrator> getAdmByNameComplete(String nameComplete){
        return this.administratorRepository.findByNameComplete(nameComplete);
    }
}
