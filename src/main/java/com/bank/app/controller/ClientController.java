package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.Login;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.infrastructure.token.TokenService;
import com.bank.app.usecase.client.ClientDto;
import com.bank.app.usecase.client.Create;
import com.bank.app.usecase.client.Delete;
import com.bank.app.usecase.client.Search;
import com.bank.app.usecase.client.Update;

@Controller
@RestController
public class ClientController {
    @Autowired
    private Create clientCreate;
    @Autowired
    private Search clientSearch;
    @Autowired
    private Update clientUpdate;
    @Autowired
    private Delete clientDelete;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/client", produces = "application/json")
    public ResponseEntity<?> saveClient(@RequestBody ClientDto data) {
        try {
            String code = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(code);
            Client client = new Client(
                    data.getCpf(),
                    data.getNameComplete(),
                    data.getEmail(),
                    data.getPassword(),
                    data.getAccount(),
                    data.getPhone(),
                    data.getAddress());
            Client result = this.clientCreate.createClient(client);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @PostMapping(value = "/client/cards/{id}", produces = "application/json")
    public ResponseEntity<?> saveCards(@RequestBody List<Card> data, @PathVariable("id") String id) {
        try {
            Client client = this.clientSearch.getClientById(id);
            List<Card> cards = data;
            client.getCards().forEach(cards::add);
            client.setCards(cards);
            Client result = this.clientUpdate.updateClient(client);
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PostMapping(value = "/client/login", produces = "application/json")
    public ResponseEntity<?> loginClient(@RequestBody Login login) {
        try {

            var userAuthentication = new UsernamePasswordAuthenticationToken(
                    login.cpf(), login.password());

            var aut = this.authenticationManager.authenticate(userAuthentication);

            var user = (Client) aut.getPrincipal();

            String token = this.tokenService.token(user);

            return new ResponseEntity<>(token, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/client/getAll")
    public ResponseEntity<?> getAll() {
        List<Client> clients = this.clientSearch.getAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    /* @GetMapping(value = "/client/cards/cpf/{cpf}")
    public ResponseEntity<?> getAllCardsByCpf(@PathVariable("cpf") String cpf) {
        try {
            Client clients = this.clientSearch.getClientById(cpf);

            return new ResponseEntity<>(clients.getCards(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
    @GetMapping(value = "/client/cards/{number}")
    public ResponseEntity<?> getCardByNumberCard(@PathVariable("number") String number) {
        try {
            Card card = this.clientSearch.getCardClient(number);

            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    } */

    @GetMapping(value = "/client/cpf/{cpf}")
    public ResponseEntity<?> getById(@PathVariable("email") String cpf) {
        try {
            Client clients = this.clientSearch.getClientById(cpf);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/client/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        try {
            List<Client> clients = this.clientSearch.getClientByEmail(email);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/client/email/{nameComplete}")
    public ResponseEntity<?> getByNameComplete(@PathVariable("nameComplete") String nameComplete) {
        try {
            List<Client> clients = this.clientSearch.getClientByNameComplete(nameComplete);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/client/{id}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ClientDto data) {
        try {
            Client client = this.clientSearch.getClientById(id);

            client.setNameComplete(data.getNameComplete() != null ? data.getNameComplete() : client.getNameComplete());
            client.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : client.getPassword());
            client.setEmail(data.getEmail() != null ? data.getEmail() : client.getEmail());
            client.setCpf(data.getEmail() != null ? data.getCpf() : client.getCpf());
            client.setPhone(data.getPhone() != null ? data.getPhone() : client.getPhone());
            client.setAccount(data.getAccount() != null ? data.getAccount() : client.getAccount());
            client.setAddress(data.getAddress() != null ? data.getAddress() : client.getAddress());
            client.setUpdateAt(LocalDateTime.now());

            Client update = this.clientUpdate.updateClient(client);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/client/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.clientDelete.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
