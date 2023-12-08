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
@Table(name="MEME_TAG")
public class MemeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEME_TAG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "밈은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "MEME_ID", updatable = false)
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "태그는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "TAG_ID", updatable = false)
    private Tag tag;

    @Builder
    public MemeTag(Meme meme, Tag tag) {
        this.meme = meme;
        this.tag = tag;
    }
}
