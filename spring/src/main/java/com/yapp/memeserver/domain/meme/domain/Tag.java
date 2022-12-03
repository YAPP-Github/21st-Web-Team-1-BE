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
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "카테고리는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "category_id", updatable = false)
    private Category category;

    private Integer viewCount;

    @Builder
    public Tag(Long id, String name) {
        this.name = name;
        this.viewCount = 0;
    }

    public void updateTag(String name) {
        this.name = name;
    }
}
