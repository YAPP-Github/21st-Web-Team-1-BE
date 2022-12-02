package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meme_id")
    private Long id;

    @NotNull(message = "제목은 필수로 입력되어야 합니다.")
    @Column(length = 100)
    private String title;

    @NotNull(message = "이미지는 필수로 입력되어야 합니다.")
    @Column(length = 200)
    private String imageUrl;

    private Integer viewCount;

    @Builder
    public Meme(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.viewCount = 0;
    }

    public void updateMeme(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
