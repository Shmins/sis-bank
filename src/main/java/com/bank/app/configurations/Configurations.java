package com.bank.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Configurations {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String roleClient = "ROLE_CLIENT";
        String roleOfficial = "ROLE_OFFICIAL";
        String roleAdm = "ROLE_ADM";
        String roleBoss = "ROLE_BOSS";

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "client/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "client/v1/**").permitAll()
                        .requestMatchers("boss/**").hasAnyAuthority(roleBoss)
                        .requestMatchers("adm/**").hasAnyAuthority(roleAdm, roleBoss)
                        .requestMatchers("offical/**").hasAnyAuthority(roleOfficial, roleAdm, roleBoss)
                        .requestMatchers("client/v1/cards/**").hasAnyAuthority(roleClient, roleOfficial, roleAdm, roleBoss)

                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
