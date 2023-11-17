package com.bank.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.bank.app.entity.administrator.model.Administrator;
import com.bank.app.entity.administrator.repository.AdministratorRepository;
import com.bank.app.entity.client.model.NumberAgency;

@DataMongoTest
@ActiveProfiles("test")
class AdministratorRepositoryTest {
    @Autowired
    AdministratorRepository administratorRepository;

    @Test
    @DisplayName("create administrator successfully")
    void createBossCase1() {
        Administrator boss = getAdm();

        Administrator data = this.administratorRepository.save(boss);

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get administrator successfully")
    void findByCpfCase1() {

        UserDetails data = this.administratorRepository.findByCpf(getAdm().getCpf());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get administrator failed")
    void findByCpfCase2() {
        UserDetails data = this.administratorRepository.findByCpf("000.000.000-00");

        assertThat(data).isNull();
    }
    @Test
    @DisplayName("Get administrator by rg successfully")
    void findByRgCase1() {

        Administrator data = this.administratorRepository.findByRg(getAdm().getRg());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get administrator by rg failed")
    void findByRgCase2() {
        Administrator data = this.administratorRepository.findByRg("000.000.000-00");

        assertThat(data).isNull();
    }

    
    private Administrator getAdm() {
        Administrator adm = new Administrator();

        adm.setCpf("096.879.823.30");
        adm.setRg("12345678911");
        adm.setNumberAgency(new NumberAgency(null, null));
        adm.setNameComplete("Pedro Alyson Teixeira dos Santos");
        adm.setPassword("123456789");
        adm.setRole("ROLE_ADM");
        adm.setCreateAt(LocalDateTime.now());
        adm.setUpdateAt(LocalDateTime.now());

        return adm;
    }
}
