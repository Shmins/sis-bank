package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.client.exception.GenericException;
import com.bank.app.entity.client.model.Account;
import com.bank.app.entity.client.model.Client;
import com.bank.app.entity.client.model.Login;
import com.bank.app.entity.client.model.cardmodel.Card;
import com.bank.app.infrastructure.token.TokenService;
import com.bank.app.infrastructure.token.TokenUserTdo;
import com.bank.app.usecase.account.AccountDto;
import com.bank.app.usecase.client.ClientDto;
import com.bank.app.usecase.client.ClientService;

import jakarta.annotation.security.RolesAllowed;

@Controller
@RestController
@RequestMapping("client/v1")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "", produces = "application/json")
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
            Client result = this.clientService.createClient(client);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @PostMapping(value = "{cpf}", produces = "application/json")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto data, @PathVariable("cpf") String cpf) {
        try {
            Client client = this.clientService.getClientById(cpf);
            Account account = new Account(data.getNameComplete(), data.getTypeAccount(), data.getCpf());
            List<Account> ac = client.getAccount();
            ac.add(account);
            client.setAccount(ac);
            Client result = this.clientService.updateClient(client);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @RolesAllowed("CLIENT")
    @PostMapping(value = "/cards/{cpf}", produces = "application/json")
    public ResponseEntity<?> saveCards(@RequestBody Card data, @PathVariable("cpf") String cpf) {
        try {
            Client client = this.clientService.getClientById(cpf);
            if (client.getCards().size() == 6) {
                throw new GenericException("limite de cartões exedido");
            }
            List<Card> cards = client.getCards().isEmpty() ? new ArrayList<>() : client.getCards();

            cards.add(data);

            client.setCards(cards);
            Client result = this.clientService.updateClient(client);
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> loginClient(@RequestBody Login login) {
        try {
            var userAuthentication = new UsernamePasswordAuthenticationToken(
                    login.cpf(), login.password());

            var aut = this.authenticationManager.authenticate(userAuthentication);

            var user = (Client) aut.getPrincipal();

            String token = this.tokenService.token(new TokenUserTdo(user.getCpf(), user.getCpf(), user.getRole()));

            return new ResponseEntity<>(token, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(401));
        }
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        List<Client> clients = this.clientService.getAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/cards/cpf/{cpf}")
    public ResponseEntity<?> getAllCardsByCpf(@PathVariable("cpf") String cpf) {
        try {
            Client clients = this.clientService.getClientById(cpf);

            return new ResponseEntity<>(clients.getCards(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cards/{number}")
    public ResponseEntity<?> getCardByNumberCard(@PathVariable("number") String number) {
        try {
            Client client = this.clientService.getCardClient(number);
            var card = client.getCards().stream().filter(res -> number.equals(res.getNumberCard()));
            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<?> getById(@PathVariable("cpf") String cpf) {
        try {
            Client clients = this.clientService.getClientById(cpf);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        try {
            List<Client> clients = this.clientService.getClientByEmail(email);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @GetMapping(value = "/email/{nameComplete}")
    public ResponseEntity<?> getByNameComplete(@PathVariable("nameComplete") String nameComplete) {
        try {
            List<Client> clients = this.clientService.getClientByNameComplete(nameComplete);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/cards/{number}", produces = "application/json")
    public ResponseEntity<?> updateCardById(@PathVariable("number") String number, @RequestBody Card data) {
        try {
            Client client = this.clientService.getCardClient(number);

            Card card = client.getCards().stream().filter(res -> number.equals(res.getNumberCard())).toList().get(0);

            card.setNumberCard(data.getNumberCard() != null ? data.getNumberCard() : card.getNumberCard());
            card.setTypeIssuer(data.getTypeIssuer() != null ? data.getTypeIssuer() : card.getTypeIssuer());
            card.setValidityDate(data.getValidityDate() != null ? data.getValidityDate() : card.getValidityDate());
            card.setTypeCard(data.getTypeCard() != null ? data.getTypeCard() : card.getTypeCard());
            card.setUpdateAt(LocalDateTime.now());

            var cards = client.getCards().stream().filter(res -> !number.equals(res.getNumberCard())).toList();
            client.setCards(cards);
            this.clientService.updateClient(client);
            return new ResponseEntity<>(cards, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ClientDto data) {
        try {
            Client client = this.clientService.getClientById(id);

            client.setNameComplete(data.getNameComplete() != null ? data.getNameComplete() : client.getNameComplete());
            client.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : client.getPassword());
            client.setEmail(data.getEmail() != null ? data.getEmail() : client.getEmail());
            client.setPhone(data.getPhone() != null ? data.getPhone() : client.getPhone());
            client.setAddress(data.getAddress() != null ? data.getAddress() : client.getAddress());
            client.setUpdateAt(LocalDateTime.now());

            Client update = this.clientService.updateClient(client);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.clientService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping(value = "/cards/{number}")
    public ResponseEntity<?> deleteCardById(@PathVariable("number") String number) {
        try {
            Client client = this.clientService.getCardClient(number);
            List<Card> card = client.getCards().stream().filter(res -> !number.equals(res.getNumberCard())).toList();
            client.setCards(card);
            this.clientService.updateClient(client);
            return new ResponseEntity<>(card, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
