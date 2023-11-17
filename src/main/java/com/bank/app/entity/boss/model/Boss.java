package com.bank.app.entity.boss.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bank.app.entity.client.exception.CpfException;
import com.bank.app.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "boss")
public class Boss extends User{

    public Boss(String cpf, String nameComplete, String password){
        super(cpf, password, nameComplete, "ROLE_BOSS", LocalDateTime.now(), LocalDateTime.now());
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
            throw new CpfException("Formato do cpf inválido");
        }
        if(Boolean.FALSE.equals(super.verifyCpfCode(cpf))){
            throw new IllegalArgumentException("Cpf com formato errado");
        }

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
        return true;
    }

}
