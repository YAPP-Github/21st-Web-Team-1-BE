package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "카테고리는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "CATEGORY_ID", updatable = false)
    private Category category;

    @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    @URL
    @Size(max = 2048)
    @Column(name = "IMAGE_URL")
    private String imageUrl;


    // 연관관계 편의 메소드
    protected void setCategory(Category category) {
        this.category = category;
    }

    @Builder
    public Tag(String name, Category category) {
        this.name = name;
        this.category = category;
        this.viewCount = 0;
    }

    public void updateTag(String name) {
        this.name = name;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
