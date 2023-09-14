package com.bank.app.infrastructure.token;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
@Service
public class TokenService {
    @Value("spring.datasource.secret")
    private String password;

    public String token(TokenUserTdo user) {
        return JWT.create()
                .withSubject(user.Username())
                .withClaim("cpf", user.cpf())
                .withIssuer(user.role())
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(password));
    }
    
    public String getSubject(String token){
        return JWT.require(Algorithm.HMAC256(password))
        .build().verify(token).getSubject();
    }
    public String getIssuer(String token){
        return JWT.require(Algorithm.HMAC256(password))
        .build().verify(token).getIssuer();
    }
}
