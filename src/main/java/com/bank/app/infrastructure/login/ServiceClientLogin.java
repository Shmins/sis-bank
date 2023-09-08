package com.bank.app.infrastructure.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.client.ClientSearch;
import com.bank.app.usecase.official.OfficialSearch;

@Service
public class ServiceClientLogin implements UserDetailsService {
    @Autowired
    private ClientSearch clientRepository;
    @Autowired
    private OfficialSearch officialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (clientRepository.getClientByCpf(username) == null) {
            return officialRepository.getOfficialByCpf(username);
        } else {

            return clientRepository.getClientByCpf(username);
        }
    }

}
