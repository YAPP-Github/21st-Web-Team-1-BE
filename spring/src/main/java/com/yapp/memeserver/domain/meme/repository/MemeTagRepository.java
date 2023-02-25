package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemeTagRepository extends JpaRepository<MemeTag, Long> {

    List<MemeTag> findByMeme(Meme meme);
    Page<MemeTag> findByTag(Tag tag, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select mt.meme_id, meme_tag_id, mt.tag_id from (SELECT DISTINCT MEME_ID, MEME_TAG_ID, TAG_ID FROM MEME_TAG where meme_id <> :memeId) mt join (" +
                    "select tag_id tagId from tag t join category c on t.category_id = c.category_id where tag_id in :tagIdList order by c.priority) ts " +
                    "on mt.tag_id = ts.tagId ",
            countQuery = "select count(*) from (SELECT DISTINCT MEME_ID, MEME_TAG_ID, TAG_ID FROM MEME_TAG where meme_id <> :memeId) mt join (" +
                         "select tag_id tagId from tag t  join category c on t.category_id = c.category_id where tag_id in :tagIdList) ts")
    Page<MemeTag> findByRelTag(@Param("memeId") Long memeId, @Param("tagIdList") List<Long> tagIdList, Pageable pageable);
}
