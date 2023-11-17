package com.bank.app.entity.official.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bank.app.entity.client.exception.CpfException;
import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "official")
public class Official extends User {
    private String rg;

    private String email;

    private Address address;

    private Boolean isAuthorized;

    public Official(String cpf, String rg, String nameComplete, String email, String password,
            Address address) {
        super(cpf, password, nameComplete, "ROLE_OFFICIAL", LocalDateTime.now(), LocalDateTime.now());
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
            throw new CpfException("Formato do cpf inválido");
        }
        if(Boolean.FALSE.equals(super.verifyCpfCode(cpf))){
            throw new IllegalArgumentException("Cpf com formato errado");
        }
        if (rg.length() != 11) {
            throw new GenericException("RG com formato inválido");
        }
        this.rg = rg;
        this.email = email;
        this.address = address;
        this.isAuthorized = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(super.getRole()));
    }

    @Override
    public String getUsername() {
        return super.getCpf();
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
        return this.isAuthorized;
    }
}
