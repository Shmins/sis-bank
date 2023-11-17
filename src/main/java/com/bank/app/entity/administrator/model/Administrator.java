package com.bank.app.entity.administrator.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bank.app.entity.client.exception.CpfException;
import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.NumberAgency;
import com.bank.app.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "administrator")
public class Administrator extends User{

    private String rg;

    private NumberAgency numberAgency;

    public Administrator(String cpf, String rg, String nameComplete, String password, NumberAgency numberAgency) {
        
        super(cpf, password, nameComplete, "ROLE_ADM", LocalDateTime.now(), LocalDateTime.now());
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
        this.numberAgency = numberAgency;
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
