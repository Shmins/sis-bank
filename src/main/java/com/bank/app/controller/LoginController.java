package com.bank.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.Login;
import com.bank.app.entity.official.model.Official;
import com.bank.app.infrastructure.token.TokenService;
import com.bank.app.infrastructure.token.TokenUserTdo;

@Controller
@RestController
@RequestMapping("login/v1")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            var userAuthentication = new UsernamePasswordAuthenticationToken(
                    login.cpf(), login.password());

            var aut = this.authenticationManager.authenticate(userAuthentication);
            var role = aut.getAuthorities().stream().toList().get(0).getAuthority();
            String token;
            switch (role) {
                case ("ROLE_CLIENT"): {
                    var user = (Client) aut.getPrincipal();
                    token = this.tokenService
                            .token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));
                    return new ResponseEntity<>(token, HttpStatus.valueOf(200));

                }
                case ("ROLE_OFFICIAL"): {
                    var user = (Official) aut.getPrincipal();
                    token = this.tokenService
                            .token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));
                    return new ResponseEntity<>(token, HttpStatus.valueOf(200));

                }
                case ("ROLE_ADM"): {
                    var user = (Administrator) aut.getPrincipal();
                    token = this.tokenService
                            .token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));
                    return new ResponseEntity<>(token, HttpStatus.valueOf(200));

                }
                case ("ROLE_BOSS"): {
                    var user = (Boss) aut.getPrincipal();
                    token = this.tokenService
                            .token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));
                    return new ResponseEntity<>(token, HttpStatus.valueOf(200));

                }
                default:
                    return new ResponseEntity<>(HttpStatus.valueOf(401));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(401));

        }
    }
}
