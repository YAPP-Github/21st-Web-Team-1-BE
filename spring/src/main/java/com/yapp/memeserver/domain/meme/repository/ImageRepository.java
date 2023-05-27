package com.yapp.memeserver.domain.meme.repository;

import com.yapp.memeserver.domain.meme.domain.Image;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByMeme(Meme meme);
    long count();

    @Query(nativeQuery = true,
            value = "SELECT ANY_VALUE(i.IMAGE_URL) FROM IMAGE i\n" +
                    "JOIN (\n" +
                    "\tSELECT m.MEME_ID mid FROM MEME_TAG mt\n" +
                    "\tJOIN MEME m ON mt.TAG_ID = :tagId AND mt.MEME_ID = m.MEME_ID\n" +
                    "\tORDER BY SHARE_COUNT DESC LIMIT 10\n" +
                    "\t) m\n" +
                    "ON m.mid = i.MEME_ID\n" +
                    "GROUP BY i.MEME_ID;\n"
    )
    List<String> findTagRankImageList(@Param("tagId") Long tagId);

}
