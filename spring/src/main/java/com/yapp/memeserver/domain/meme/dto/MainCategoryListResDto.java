package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.MainCategory;
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
public class MainCategoryListResDto {

    private List<MainCategoryListResDto.SingleMainCategory> mainCategories;

    @Getter
    public static class SingleMainCategory {
        private Long mainCategoryId;
        private String name;
        private String icon;
        private Integer priority;
        private Boolean hasSub;
        private CategoryListResDto categories;

        public SingleMainCategory(MainCategory mainCategory, HashMap<Category, List<Tag>> categoryMap) {
            this.mainCategoryId = mainCategory.getId();
            this.name = mainCategory.getName();
            this.icon = mainCategory.getIcon();
            this.priority = mainCategory.getPriority();
            this.hasSub = mainCategory.getHasSub();
            this.categories = CategoryListResDto.of(categoryMap);
        }

        public static MainCategoryListResDto.SingleMainCategory of(MainCategory mainCategory, HashMap<Long, HashMap<Category, List<Tag>>> categoryMap) {
            return new SingleMainCategory(mainCategory, categoryMap.get(mainCategory.getId()));
        }
    }
    public static MainCategoryListResDto of(List<MainCategory> mainCategoryList, HashMap<Long, HashMap<Category, List<Tag>>> categoryMap) {
        return MainCategoryListResDto.builder()
                .mainCategories(mainCategoryList.stream().map(x-> MainCategoryListResDto.SingleMainCategory.of(x, categoryMap)).collect(Collectors.toList()))
                .build();
    }
}
