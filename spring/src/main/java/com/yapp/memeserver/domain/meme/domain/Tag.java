package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Column(length = 50)
    private String name;

    @Builder
    public Tag(Long id, String name) {
        this.name = name;
    }

    public void updateTag(String name) {
        this.name = name;
    }
}
