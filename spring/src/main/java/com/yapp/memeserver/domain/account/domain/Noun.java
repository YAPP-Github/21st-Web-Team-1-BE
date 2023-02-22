package com.yapp.memeserver.domain.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Noun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noun_id", updatable = false)
    private Long id;

    @NotNull(message = "word는 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 24)
    private String word;

    @Builder
    public Noun(String word) {
        this.word = word;
    }
}
