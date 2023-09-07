package com.bank.app.entity.administrator.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bank.app.entity.client.exception.CpfException;
import com.bank.app.entity.client.exception.GenericException;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Document(collection = "Administrator")
public class Administrator implements UserDetails{
    
    private String cpf;

    private String rg;

    private String password;

    private String nameComplete;

    private String bankAgency;

    private String role;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Administrator(String cpf, String rg, String nameComplete, String password, String bankAgency) {
        if (cpf == null || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
            throw new CpfException("Formato do cpf inválido");
        }

        if (rg.matches("\\d{10}\\-\\d")) {
            throw new GenericException("RG com formato inválido");
        }
        this.cpf = cpf;
        this.nameComplete = nameComplete;
        this.password = password;
        this.bankAgency = bankAgency;
        this.role = "ROLE_ADM";
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
       return this.cpf;
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
