package com.bank.app.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.usecase.boss.BossCreate;
import com.bank.app.usecase.boss.BossDelete;
import com.bank.app.usecase.boss.BossDto;
import com.bank.app.usecase.boss.BossSearch;
import com.bank.app.usecase.boss.BossUpdate;


import jakarta.annotation.security.RolesAllowed;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.client.model.Login;
import com.bank.app.infrastructure.token.TokenService;
import com.bank.app.infrastructure.token.TokenUserTdo;

@RestController
@RequestMapping("boss/v1")
@CrossOrigin("*")

public class BossController {
    @Autowired
    private BossCreate bossCreate;
    @Autowired
    private BossSearch bossSearch;
    @Autowired
    private BossUpdate bossUpdate;
    @Autowired
    private BossDelete bossDelete;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @RolesAllowed("BOSS")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> saveOfficial(@RequestBody BossDto data) {
        try {
            String code = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(code);
            Boss official = new Boss(
                    data.getCpf(),
                    data.getNameComplete(),
                    data.getPassword());
            Boss result = this.bossCreate.createBoss(official);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }
   
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> loginClient(@RequestBody Login login) {
        try {
            var userAuthentication = new UsernamePasswordAuthenticationToken(
                    login.cpf(), login.password());

            var aut = this.authenticationManager.authenticate(userAuthentication);

            var user = (Boss) aut.getPrincipal();

            String token = this.tokenService.token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));

            return new ResponseEntity<>(token, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(401));
        }
    }
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<?> getById(@PathVariable("cpf") String cpf) {
        try {
            Boss boss = this.bossSearch.getBossById(cpf);

            return new ResponseEntity<>(boss, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }
    @RolesAllowed("BOSS")
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody BossDto data) {
        try {
            Boss boss = this.bossSearch.getBossById(id);

            boss.setNameComplete(
                    data.getNameComplete() != null ? data.getNameComplete() : boss.getNameComplete());
            boss.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : boss.getPassword());

            boss.setUpdateAt(LocalDateTime.now());

            Boss update = this.bossUpdate.updateBoss(boss);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @RolesAllowed("BOSS")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.bossDelete.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}