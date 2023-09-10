package com.bank.app.infrastructure.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.app.usecase.administrator.AdministratorService;
import com.bank.app.usecase.boss.BossService;
import com.bank.app.usecase.client.ClientService;
import com.bank.app.usecase.official.OfficialService;

@Service
public class ServiceClientLogin implements UserDetailsService {
    @Autowired
    private ClientService clientRepository;
    @Autowired
    private OfficialService officialService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private BossService bossService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails client = clientRepository.getClientByCpf(username);
        if (client == null) {
            UserDetails official = officialService.getOfficialByCpf(username);
            if(official == null){
                UserDetails adm = administratorService.getAdmByCpf(username);
                if(adm == null){
                    return bossService.getBossByCpf(username);
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
