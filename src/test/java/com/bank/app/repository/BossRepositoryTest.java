package com.bank.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.bank.app.entity.boss.model.Boss;
import com.bank.app.entity.boss.repository.BossRepository;

@DataMongoTest
@ActiveProfiles("test")
class BossRepositoryTest {
    @Autowired
    BossRepository bossRepository;

    @Test
    @DisplayName("create boss successfully")
    void createBossCase1() {
        Boss boss = getBoss();

        Boss data = this.bossRepository.save(boss);

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get boss successfully")
    void findByCpfCase1() {

        UserDetails data = this.bossRepository.findByCpf(getBoss().getCpf());

        assertThat(data).isNotNull();
    }

    @Test
    @DisplayName("Get boss failed")
    void findByCpfCase2() {
        UserDetails data = this.bossRepository.findByCpf("000.000.000-00");

        assertThat(data).isNull();
    }

    private Boss getBoss() {
        Boss boss = new Boss();

        boss.setCpf("096.879.823.30");
        boss.setNameComplete("Pedro Alyson Teixeira dos Santos");
        boss.setPassword("123456789");
        boss.setRole("ROLE_BOSS");
        boss.setCreateAt(LocalDateTime.now());
        boss.setUpdateAt(LocalDateTime.now());

        return boss;
    }
}
