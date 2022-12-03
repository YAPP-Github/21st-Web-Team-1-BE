package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Column(length = 50)
    private String name;

    public void updateCategory(String name) {
        this.name = name;
    }
}