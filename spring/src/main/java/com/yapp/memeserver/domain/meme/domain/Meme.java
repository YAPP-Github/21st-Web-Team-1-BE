package com.yapp.memeserver.domain.meme.domain;

import com.yapp.memeserver.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meme extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meme_id")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "이미지 URL은 필수로 입력되어야 합니다.")
    @URL
    @Size(max = 2048)
    private String imageUrl;

    private Integer viewCount;

    @Builder
    public Meme(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.viewCount = 0;
    }

    public void updateMeme(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}
