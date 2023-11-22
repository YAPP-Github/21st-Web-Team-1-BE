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
@Table(name="IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @NotNull(message = "URL은 필수로 입력되어야 합니다.")
    @URL
    @Size(max = 2048)
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @NotNull(message = "가로 길이는 필수로 입력되어야 합니다.")
    @Column(name = "WIDTH")
    private Integer width;

    @NotNull(message = "세로 길이는 필수로 입력되어야 합니다.")
    @Column(name = "HEIGHT")
    private Integer height;

    private String ahash;

    private String dhash;

    private String phash;

    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "밈은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "MEME_ID", updatable = false)
    private Meme meme;


    // 연관관계 편의 메소드
    protected void setMeme(Meme meme) {
        this.meme = meme;
    }

    @Builder
    public Image(String imageUrl, Integer width, Integer height, Meme meme, String ahash, String dhash, String phash, Integer priority) {
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
        this.meme = meme;
        this.ahash = ahash;
        this.dhash = dhash;
        this.phash = phash;
        this.priority = priority;
    }
}