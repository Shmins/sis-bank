package com.bank.app.infrastructure.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.administrator.AdministratorSearch;
import com.bank.app.usecase.boss.BossSearch;
import com.bank.app.usecase.client.ClientSearch;
import com.bank.app.usecase.official.OfficialSearch;

@Service
public class ServiceClientLogin implements UserDetailsService {
    @Autowired
    private ClientSearch clientRepository;
    @Autowired
    private OfficialSearch officialRepository;
    @Autowired
    private AdministratorSearch administratorSearch;
    @Autowired
    private BossSearch bossSearch;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails client = clientRepository.getClientByCpf(username);
        if (client == null) {
            UserDetails official = officialRepository.getOfficialByCpf(username);
            if(official == null){
                UserDetails adm = administratorSearch.getAdmByCpf(username);
                if(adm == null){
                    return bossSearch.getBossByCpf(username);
                }
                else{
                    return adm;
                }
            }
            else{
                return official;
            }

        } else {
            return client;
        }
    }

}
