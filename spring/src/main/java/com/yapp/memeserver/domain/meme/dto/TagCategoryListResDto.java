package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCategoryListResDto {

    private List<SingleCategory> categories;

    @Getter
    public static class SingleCategory {
        private Long categoryId;
        private String name;
        private Integer priority;
        private List<SingleTagDto> tags;

        public SingleCategory(Category category, List<SingleTagDto> tagList) {
            this.name = category.getName();
            this.categoryId = category.getId();
            this.priority = category.getPriority();
            this.tags = tagList;
        }

        public static SingleCategory of(Category category) {
            return new SingleCategory(category, category.getTagList().stream().map(SingleTagDto::of).collect(Collectors.toList()));
        }
    }

    public static TagCategoryListResDto of(List<Category> categoryList) {
        return TagCategoryListResDto.builder()
                .categories(categoryList.stream().map(SingleCategory::of).collect(Collectors.toList()))
                .build();
    }
}
