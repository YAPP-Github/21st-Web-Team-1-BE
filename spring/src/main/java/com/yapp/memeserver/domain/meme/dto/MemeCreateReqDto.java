package com.yapp.memeserver.domain.meme.dto;

import com.yapp.memeserver.domain.meme.domain.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemeCreateReqDto {

    @Getter
    public static class NewSingleTag {
        private String name;
        private Long categoryId;
    }

    @Getter
    public static class SingleTag {
        private Long tagId;
    }

    private String name;

    private String description;

    private List<SingleTag> tags;

    private List<NewSingleTag> newTags;

}
