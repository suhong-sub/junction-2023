package com.example.security.jwt.tamagotchi;

import com.example.security.jwt.account.domain.Account;
import lombok.Builder;

import java.util.List;

public record ResponseTamagotchi() {

    @Builder
    public record Info(
            List<Tamagotchi> tamagotchis // 그냥 보내주자
    ) {

    }
}
