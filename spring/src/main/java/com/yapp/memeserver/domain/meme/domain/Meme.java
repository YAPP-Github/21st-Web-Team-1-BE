package com.yapp.memeserver.domain.meme.domain;

import com.yapp.memeserver.domain.account.domain.Account;
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
    @Column(name = "MEME_ID")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 255)
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    @Column(name = "SHARE_COUNT")
    private Integer shareCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", updatable = false)
    private Account writer;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private MemeStatus status;

    @OneToMany(mappedBy = "meme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addImage(Image image, Integer priority) {
        imageList.add(image);
        image.setMeme(this);
        image.setPriority(priority);
    }

    @Builder
    public Meme(String name, String description, Account writer, MemeStatus status) {
        this.name = name;
        this.description = description;
        this.viewCount = 0;
        this.shareCount = 0;
        this.writer = writer;
        this.status = status;
    }

    public void updateMeme(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void successCreate() {
        this.status = MemeStatus.ACTIVE ;
    }
}
