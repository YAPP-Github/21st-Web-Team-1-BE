package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.account.dto.AccountInfoResDto;
import com.yapp.memeserver.domain.account.dto.MyAccountResDto;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemeDetailResDto {

    private Long memeId;
    private String name;
    private String description;
    private Integer viewCount;
    private Integer shareCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String writer;
    private List<ImageResDto> images;
    private List<TagResDto> tags;

    public static MemeDetailResDto of(Meme meme, List<Tag> tagList) {
        return MemeDetailResDto.builder()
                .memeId(meme.getId())
                .name(meme.getName())
                .description(meme.getDescription())
                .viewCount(meme.getViewCount())
                .shareCount(meme.getShareCount())
                .createdDate(meme.getCreatedDate())
                .modifiedDate(meme.getModifiedDate())
                .writer(meme.getWriter().getName())
                .images(meme.getImageList().stream().map(ImageResDto::of).collect(Collectors.toList()))
                .tags(tagList.stream().map(TagResDto::of).collect(Collectors.toList()))
                .build();
    }
}
