package com.bank.app.entity.client.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.exception.CpfException;
import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.cardmodel.Card;
import lombok.Data;

@Data
@Document(collection = "client")
public class Client implements UserDetails {
    @Id
    private String cpf;

    private String nameComplete;

    private String email;

    private String password;

    private String typeAccount;

    private Phone phone;

    private Address address;

    private BorrowedLimit borrowedLimit;
    
    private List<Card> cards = new ArrayList<>();

    private String role;
    
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Client(String cpf, String nameComplete, String email, String password, String typeAccount, Phone phone,
            Address address) {
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
            throw new CpfException("Formato do cpf inválido");
        }
         if(!typeAccount.equals("chain") && !typeAccount.equals("savings")){
            throw new GenericException("Tipo de conta inválido");
        }
        this.cpf = cpf;
        this.nameComplete = nameComplete;
        this.email = email;
        this.password = password;
        this.typeAccount = typeAccount;
         
        this.phone = phone;
        this.address = address;
        this.role = "ROLE_CLIENT";
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.borrowedLimit = new BorrowedLimit(0, 100000);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
