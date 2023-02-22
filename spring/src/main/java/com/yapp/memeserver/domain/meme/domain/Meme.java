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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="MEME")
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

    private Integer viewCount;

    private Integer shareCount;

    @OneToMany(mappedBy = "meme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addImage(Image image) {
        imageList.add(image);
        image.setMeme(this);
    }

    @Builder
    public Meme(String name, String description) {
        this.name = name;
        this.description = description;
        this.viewCount = 0;
        this.shareCount = 0;
    }

    public void updateMeme(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
