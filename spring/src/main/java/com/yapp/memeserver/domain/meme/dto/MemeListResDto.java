package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Meme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemeListResDto {

    private List<SingleMeme> memes;
    private Integer count;

    @Getter
    public static class SingleMeme {
        private Long memeId;
        private String name;
        private String description;
        private String imageUrl;
        private Integer viewCount;
        private Integer shareCount;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public SingleMeme(Meme meme) {
            this.memeId = meme.getId();
            this.name = meme.getName();
            this.description = meme.getDescription();
            this.imageUrl = meme.getImageUrl();
            this.viewCount = meme.getViewCount();
            this.shareCount = meme.getShareCount();
            this.createdDate = meme.getCreatedDate();
            this.modifiedDate = meme.getModifiedDate();
        }

        public static SingleMeme of(Meme meme) {
            return new SingleMeme(meme);
        }
    }

    public static MemeListResDto of(List<Meme> memeList) {
        return MemeListResDto.builder()
                .memes(memeList.stream().map(SingleMeme::of).collect(Collectors.toList()))
                .count(memeList.size())
                .build();
    }

}
