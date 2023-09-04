package com.bank.app.infrastructure.token;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bank.app.entity.client.model.Client;
@Service
public class TokenService {
    @Value("spring.datasource.secret")
    private String password;

    public String token(Client client) {
        return JWT.create()
                .withSubject(client.getNameComplete())
                .withClaim("cpf", client.getCpf())
                .withExpiresAt(LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(password));
    }
}
