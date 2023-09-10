package com.bank.app.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.client.model.Login;
import com.bank.app.infrastructure.token.TokenService;
import com.bank.app.infrastructure.token.TokenUserTdo;
import com.bank.app.usecase.administrator.AdministratorDto;
import com.bank.app.usecase.administrator.AdministratorService;



@RestController
@RequestMapping("adm/v1")
@CrossOrigin("*")

public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> saveAdministrator(@RequestBody AdministratorDto data) {
        try {
            String code = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(code);
            Administrator adm = new Administrator(
                    data.getCpf(),
                    data.getRg(),
                    data.getNameComplete(),
                    data.getPassword(),
                    data.getBankAgency());
            Administrator result = this.administratorService.createAdministrator(adm);

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

            var user = (Administrator) aut.getPrincipal();

            String token = this.tokenService.token(new TokenUserTdo(user.getCpf(), user.getUsername(), user.getRole()));

            return new ResponseEntity<>(token, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(401));
        }
    }
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<?> getById(@PathVariable("cpf") String cpf) {
        try {
            Administrator clients = this.administratorService.getAdmById(cpf);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody AdministratorDto data) {
        try {
            Administrator adm = this.administratorService.getAdmById(id);

            adm.setNameComplete(data.getNameComplete() != null ? data.getNameComplete() : adm.getNameComplete());
            adm.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : adm.getPassword());
            adm.setCpf(data.getCpf() != null ? data.getCpf() : adm.getCpf());
            adm.setRg(data.getRg() != null ? data.getRg() : adm.getRg());
            adm.setBankAgency(data.getBankAgency() != null ? data.getBankAgency() : adm.getBankAgency());

            adm.setUpdateAt(LocalDateTime.now());

            Administrator update = this.administratorService.updateAdministrator(adm);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.administratorService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
