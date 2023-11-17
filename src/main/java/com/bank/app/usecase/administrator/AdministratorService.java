package com.bank.app.usecase.administrator;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.administrator.model.Administrator;

public interface AdministratorService {
    Administrator createAdministrator(Administrator adm);
    void insertMany(Iterable<Administrator> adm);
    void deleteById(String cpf);
    Administrator getAdmById(String cpf);
    UserDetails getAdmByCpf(String cpf);
    List<Administrator> getAll();
    Administrator getAdmByRg(String rg);
    Administrator getAdmByNameComplete(String nameComplete);
    Administrator updateAdministrator(Administrator adm);
}
