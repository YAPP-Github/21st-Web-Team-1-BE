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
public class MemeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meme_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "밈은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "meme_id", updatable = false)
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "태그는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "tag_id", updatable = false)
    private Tag tag;

    @Builder
    public MemeTag(Meme meme, Tag tag) {
        this.meme = meme;
        this.tag = tag;
    }
}
