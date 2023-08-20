package com.example.security.jwt.tamagotchi;

import com.example.security.jwt.account.domain.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tamagotchi")
@Getter
@NoArgsConstructor
public class Tamagotchi {
    @Id
    @Column(name = "tamagotchi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "name")
    private String name;

    @Column(name = "personality", columnDefinition = "TEXT")
    private String personality;

    @Column(name = "intimacy_rate")
    private Integer intimacyRate;

    @Column(name = "type")
    private Integer type;

    @Builder
    public Tamagotchi(Account account, String name, String personality, int type) {
        this.account = account;
        this.name = name;
        this.personality = personality;
        this.type = type;

        intimacyRate = 0;
    }

    public void increaseIntimacy() {
        this.intimacyRate++;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePersonality(String personality) {
        this.personality = personality;
    }

    public void updateType(int type) {
        this.type = type;
    }
}
