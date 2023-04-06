package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Long> {

    // 페이징 처리. 전체 밈 조회, 조회순 밈 조회에 사용
    Page<Tag> findAll(Pageable pageable);
    List<Tag> findByCategory(Category category);
    List<Tag> findByNameContainsOrderByViewCountDesc(String word);

    // 조회수 증가 코드. ModifiedDate를 변경시키지 않기 위함.
    @Modifying(clearAutomatically = true) // 연산 이후 영속성 컨텍스트를 clear 하도록 설정
    @Query("UPDATE Tag t SET t.viewCount = t.viewCount + 1 WHERE t.id = :tagId")
    void increaseViewCount(@Param("tagId") Long tagId);

    @Query(nativeQuery = true,
            value = "SELECT t.TAG_ID, t.NAME, t.CATEGORY_ID, t.IMAGE_URL, t.VIEW_COUNT, COUNT(*) c FROM TAG t " +
                    "JOIN MEME_TAG mt " +
                    "ON t.TAG_ID = mt.TAG_ID " +
                    "WHERE t.CATEGORY_ID = :categoryId " +
                    "GROUP BY t.TAG_ID " +
                    "HAVING c>1 " +
                    "ORDER BY t.name"
    )
    List<Tag> findCategoryTag(@Param("categoryId") Long categoryId);
}
