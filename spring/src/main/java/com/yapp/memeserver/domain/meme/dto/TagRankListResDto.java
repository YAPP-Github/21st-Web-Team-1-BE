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
    private Integer count;


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
            Category category = tag.getCategory();
            if (category != null) {
                this.categoryId = category.getId();
                this.categoryName = category.getName();
            }
            this.imageUrl = imageUrl;
        }
    }

    public static TagRankListResDto of(List<Tag> tagList, List<String> imageList) {
        List<TagRankResDto> tagRankResDtoList = tagList.stream()
                .map(tag -> {
                    int index = tagList.indexOf(tag);
                    String imageUrl = index < imageList.size() ? imageList.get(index) : null;
                    return new TagRankResDto(tag, imageUrl);
                })
                .collect(Collectors.toList());
        return TagRankListResDto.builder()
                .tags(tagRankResDtoList)
                .count(tagList.toArray().length)
                .build();
    }
}