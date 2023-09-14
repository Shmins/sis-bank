package com.bank.app.configurations;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bank.app.infrastructure.token.TokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class Configurations {

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    @Value("${spring.mail.username}")
    private String password;

    @Autowired
    @Value("${spring.mail.host}")
    private String host;

    @Autowired
    @Value("${spring.mail.port}")
    private String port;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String roleClient = "ROLE_CLIENT";
        String roleOfficial = "ROLE_OFFICIAL";
        String roleAdm = "ROLE_ADM";
        String roleBoss = "ROLE_BOSS";

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "client/v1/**").permitAll()
                        .requestMatchers("client/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "approve/v1/").authenticated()
                        .requestMatchers(HttpMethod.GET, "client/v1/getAll").hasAnyAuthority(roleAdm, roleBoss, roleOfficial)
                        .requestMatchers("login/v1/").permitAll()
                        .requestMatchers("email/v1/**").permitAll()

                        .requestMatchers("borrowing/v1/**").hasAnyAuthority(roleClient, roleOfficial, roleAdm, roleBoss)
                        .requestMatchers("approve/v1/**").hasAnyAuthority(roleAdm, roleBoss, roleOfficial)
                        .requestMatchers("adm/v1/**").hasAnyAuthority(roleAdm, roleBoss)
                        .requestMatchers("official/v1/**").hasAnyAuthority(roleOfficial, roleAdm, roleBoss)
                        .requestMatchers("boss/v1/**").hasAnyAuthority(roleBoss)

                        .anyRequest().authenticated())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
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

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
