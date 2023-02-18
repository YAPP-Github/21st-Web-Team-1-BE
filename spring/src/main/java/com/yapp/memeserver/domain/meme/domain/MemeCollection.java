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
public class MemeCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meme_collection_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "밈은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "meme_id", updatable = false)
    private Meme meme;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "콜렉션은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "collection_id", updatable = false)
    private Collection collection;

    @Builder
    public MemeCollection(Meme meme, Collection collection) {
        this.meme = meme;
        this.collection = collection;
    }
}
