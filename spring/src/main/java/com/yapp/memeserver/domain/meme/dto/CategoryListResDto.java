package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryListResDto {

    private List<CategoryListResDto.SingleCategory> categories;

    @Getter
    public static class SingleCategory {
        private Long categoryId;
        private String name;
        private Integer priority;
        private List<SimpleTagDto> tags;

        public SingleCategory(Category category, List<Tag> tagList) {
            this.categoryId = category.getId();
            this.name = category.getName();
            this.priority = category.getPriority();
            this.tags = tagList.stream().map(SimpleTagDto::of).collect(Collectors.toList());
        }

        public static SingleCategory of(Category category, List<Tag> tagList) {
            return new SingleCategory(category, tagList);
        }
    }

    public static CategoryListResDto of(HashMap<Category, List<Tag>> categoryMap) {
        return CategoryListResDto.builder()
                .categories(categoryMap.entrySet().stream().map(x-> CategoryListResDto.SingleCategory.of(x.getKey(), x.getValue())).collect(Collectors.toList()))
                .build();
    }
}
