package com.example.security.jwt.tamagotchi;

import lombok.Builder;

public record RequestTamagotchi() {

    @Builder
    public record Register(
            String name,
            String personality,
            int type
    ) {

    }

    @Builder
    public record PatchType(
            int type
    ) {

    }

}
