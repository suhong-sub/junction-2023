package com.example.security.jwt.tamagotchi;

import com.example.security.jwt.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TamagotchiRepository extends JpaRepository<Tamagotchi, Long> {
    List<Tamagotchi> findAllByAccount(Account account);
    List<Tamagotchi> findAllByOrderByIntimacyRateDesc();
}
