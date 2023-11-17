package com.bank.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.bank.app.entity.client.model.Address;
import com.bank.app.entity.official.model.Official;
import com.bank.app.entity.official.repository.OfficialRepository;

@DataMongoTest
@ActiveProfiles("test")
class OfficialRepositoryTest {
    @Autowired
    OfficialRepository officialRepository;

    @Test
    @DisplayName("create official successfully")
    void createOfficialCase1() {
        Official official = getOfficial();

        Official data = this.officialRepository.save(official);

        assertThat(data).isNotNull();
    }
    @Test
    @DisplayName("Get official successfully")
    void findByCpfCase1() {

        UserDetails data = this.officialRepository.findByCpf(getOfficial().getCpf());

        assertThat(data).isNotNull();
    }
    @Test
    @DisplayName("Get official failed")
    void findByCpfCase2() {
        UserDetails data = this.officialRepository.findByCpf("000.000.000-00");

        assertThat(data).isNull();
    }
    @Test
    @DisplayName("Get official by rg successfully")
    void findByRgCase1() {

        Official data = this.officialRepository.findByRg(getOfficial().getRg());

        assertThat(data).isNotNull();
    }
    @Test
    @DisplayName("Get official by rg failed")
    void findByRgCase2() {
        UserDetails data = this.officialRepository.findByRg("00000000000");

        assertThat(data).isNull();
    }
    @Test
    @DisplayName("Get official by name complete successfully")
    void findByNameCompleteCase1() {

        Official data = this.officialRepository.findByNameComplete(getOfficial().getNameComplete());

        assertThat(data).isNotNull();
    }
    @Test
    @DisplayName("Get official by name complete failed")
    void findByNameCompleteCase2() {
        UserDetails data = this.officialRepository.findByNameComplete("000.000.000-00");

        assertThat(data).isNull();
    }

    private Official getOfficial() {
        Official official = new Official();

        official.setCpf("096.879.823.30");
        official.setRg("12345678911");
        official.setNameComplete("Pedro Alyson Teixeira dos Santos");
        official.setEmail("pedro.123@gmail.com");
        official.setPassword("123456789");
        official.setAddress(new Address());

        return official;
    }
}
