package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.global.test.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryRepositoryTest extends RepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;
    private Category category3;

    @BeforeEach
    public void setUp() {
        category1 = createCategory("카테고리1");
        category2 = createCategory("카테고리2");
        category3 = createCategory("카테고리3");
    }

    @Test
    public void findAllByOrderByPriorityAscTest() {
        int priority1 = 90;
        int priority2 = 80;
        int priority3 = 110;

        category1.updatePriority(priority1);
        category2.updatePriority(priority2);
        category3.updatePriority(priority3);

        List<Category> result = categoryRepository.findAllByOrderByPriorityAsc();

        System.out.println("여여");
        for (Category category : result) {
            System.out.println("category = " + category.getName());
            System.out.println("category.getPriority() = " + category.getPriority());
        }
    }

    private Category createCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}
