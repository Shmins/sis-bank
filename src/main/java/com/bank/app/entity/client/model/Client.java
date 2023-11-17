package com.bank.app.entity.client.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client extends User {

    private String email;

    private Phone phone;

    private Address address;

    private Integer accountLimit;
    
    private List<Card> cards = new ArrayList<>();

    private List<Account> account = new ArrayList<>();

    public Client(String cpf, String nameComplete, String email, String password, List<Card> card, List<Account> account, Phone phone,
            Address address) {
        super(cpf, password, nameComplete, "ROLE_CLIENT", LocalDateTime.now(),LocalDateTime.now());
        if(Boolean.FALSE.equals(super.verifyCpfCode(cpf))){
            throw new IllegalArgumentException("Cpf com formato errado");
        }
        this.cards = card;
        this.email = email;
        this.account = account;
        this.phone = phone;
        this.address = address;
        this.accountLimit = 3;
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
