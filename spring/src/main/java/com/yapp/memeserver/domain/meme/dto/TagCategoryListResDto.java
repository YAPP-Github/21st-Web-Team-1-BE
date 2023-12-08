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
        private List<SingleTagFavDto> tags;

        public SingleCategory(Category category, List<SingleTagFavDto> tagList) {
            this.categoryId = category.getId();
            this.name = category.getName();
            this.priority = category.getPriority();
            this.tags = tagList;
        }

        public static SingleCategory of(Category category, List<Long> favTagIdList) {
            return new SingleCategory(category, category.getTagList().stream().map(x->SingleTagFavDto.checkFav(x, favTagIdList)).collect(Collectors.toList()));
        }
    }

    public static TagCategoryListResDto of(List<Category> categoryList, List<Long> favTagIdList) {
        return TagCategoryListResDto.builder()
                .categories(categoryList.stream().map(x->SingleCategory.of(x, favTagIdList)).collect(Collectors.toList()))
                .build();
    }
}