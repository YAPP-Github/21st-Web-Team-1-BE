package com.yapp.memeserver.domain.meme.dto;

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
public class TagListResDto {

    private List<TagListResDto.SingleTag> tags;
    private Integer count;

    @Getter
    public static class SingleTag {
        private Long tagId;
        private String name;
        private Integer viewCount;

        public SingleTag(Tag tag) {
            this.tagId = tag.getId();
            this.name = tag.getName();
            this.viewCount = tag.getViewCount();
        }

        public static TagListResDto.SingleTag of(Tag tag) {
            return new TagListResDto.SingleTag(tag);
        }
    }

    public static TagListResDto of(List<Tag> tagList) {
        return TagListResDto.builder()
                .tags(tagList.stream().map(TagListResDto.SingleTag::of).collect(Collectors.toList()))
                .count(tagList.size())
                .build();
    }
}
