package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.dto.MemeResDto;
import com.yapp.memeserver.domain.meme.dto.TagListResDto;
import com.yapp.memeserver.domain.meme.dto.TagResDto;
import com.yapp.memeserver.domain.meme.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public TagListResDto readAllTags(Pageable pageable) {
        List<Tag> tagList = tagService.findAllPaging(pageable);
        TagListResDto resDto = TagListResDto.of(tagList);
        return resDto;
    }

    @GetMapping("/{tagId}")
    @ResponseStatus(value = HttpStatus.OK)
    public TagResDto readTag(@PathVariable final Long tagId) {
        tagService.read(tagId);
        Tag tag = tagService.findById(tagId);
        TagResDto resDto = TagResDto.of(tag);
        return resDto;
    }

    @GetMapping("/search")
    @ResponseStatus(value = HttpStatus.OK)
    public TagListResDto searchTag(@RequestParam String word) {
        List<Tag> tagList = tagService.findByNameContains(word);
        TagListResDto resDto = TagListResDto.of(tagList);
        return resDto;
    }
}
