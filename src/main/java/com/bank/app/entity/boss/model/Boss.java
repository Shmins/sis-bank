package com.bank.app.entity.boss.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.exception.CpfException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boss implements UserDetails{
    @Id
    private String cpf;

    private String nameComplete;
    
    private String password;

    private String role;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Boss(String cpf, String nameComplete, String password){
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
            throw new CpfException("Formato do cpf inválido");
        }
        this.cpf = cpf;
        this.nameComplete = nameComplete;
        this.password = password;
        this.role = "ROLE_BOSS";
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
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
