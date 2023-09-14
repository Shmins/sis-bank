package com.bank.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.bank.app.usecase.official.OfficialDto;
import com.bank.app.usecase.official.OfficialService;

import jakarta.annotation.security.RolesAllowed;

import com.bank.app.entity.official.model.Official;

@RestController
@RequestMapping("official/v1")
@CrossOrigin("*")

public class OfficialController {
    @Autowired
    private OfficialService officialService;

   /*  @RolesAllowed("BOSS") */
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> saveOfficial(@RequestBody OfficialDto data) {
        try {
            String code = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(code);
            Official official = new Official(
                    data.getCpf(),
                    data.getRg(),
                    data.getNameComplete(),
                    data.getEmail(),
                    data.getPassword(),
                    data.getAddress());
            Official result = this.officialService.createOfficial(official);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<?> getById(@PathVariable("cpf") String cpf) {
        try {
            Official official = this.officialService.getOfficialById(cpf);

            return new ResponseEntity<>(official, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getById() {
        try {
            List<Official> official = this.officialService.getAll();

            return new ResponseEntity<>(official, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }
    @RolesAllowed("BOSS")
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS') or hasRole('ROLE_OFFICIAL')")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody OfficialDto data) {
        try {
            Official official = this.officialService.getOfficialById(id);

            official.setNameComplete(
                    data.getNameComplete() != null ? data.getNameComplete() : official.getNameComplete());
            official.setPassword(data.getPassword() != null ? new BCryptPasswordEncoder().encode(data.getPassword())
                    : official.getPassword());
            official.setCpf(data.getCpf() != null ? data.getCpf() : official.getCpf());
            official.setRg(data.getRg() != null ? data.getRg() : official.getRg());
            official.setEmail(data.getEmail() != null ? data.getEmail() : official.getEmail());
            official.setAddress(data.getAddress() != null ? data.getAddress() : official.getAddress());

            official.setUpdateAt(LocalDateTime.now());

            Official update = this.officialService.updateOfficial(official);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @RolesAllowed("BOSS")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.officialService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
