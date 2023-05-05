package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagRankListResDto {

    private List<TagRankResDto> tags;


    @Getter
    public static class TagRankResDto {
        private Long tagId;
        private String name;
        private Integer viewCount;
        private Long categoryId;
        private String categoryName;
        private String imageUrl;

        public TagRankResDto(Tag tag, String imageUrl) {
            this.tagId = tag.getId();
            this.name = tag.getName();
            this.viewCount = tag.getViewCount();
            this.categoryId = tag.getCategory().getId();
            this.categoryName = tag.getCategory().getName();
            this.imageUrl = imageUrl;
        }
    }

    public static TagRankListResDto of(List<Tag> tagList, List<String> imageList) {
        List<TagRankResDto> tagRankResDtoList = tagList.stream()
                .map(tag -> {
                    int index = tagList.indexOf(tag);
                    String imageUrl = imageList.get(index);
                    return new TagRankResDto(tag, imageUrl);
                })
                .collect(Collectors.toList());
        return TagRankListResDto.builder()
                .tags(tagRankResDtoList)
                .build();
    }
}