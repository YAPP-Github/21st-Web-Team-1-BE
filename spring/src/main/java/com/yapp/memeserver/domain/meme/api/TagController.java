package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.*;
import com.yapp.memeserver.domain.meme.service.CategoryService;
import com.yapp.memeserver.domain.meme.service.MemeService;
import com.yapp.memeserver.domain.meme.service.MemeTagService;
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
    private final CategoryService categoryService;
    private final MemeService memeService;
    private final MemeTagService memeTagService;

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
    public SingleTagListResDto searchTag(@RequestParam String word) {
        List<Tag> tagList = tagService.findByNameContains(word);
        SingleTagListResDto resDto = SingleTagListResDto.of(tagList);
        return resDto;
    }

    @GetMapping("/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public TagCategoryListResDto getTagCetegory() {
        List<Category> categoryList = categoryService.findAllOrderByPriority();
        TagCategoryListResDto resDto = TagCategoryListResDto.of(categoryList);
        return resDto;
    }

    @GetMapping("/memes/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public TagListResDto getTagMeme(@PathVariable final Long memeId) {
        Meme meme = memeService.findById(memeId);
        List<MemeTag> memeTagList = memeTagService.findByMeme(meme);
        List<Tag> tagList = memeTagService.findTagMemeList(memeTagList);
        TagListResDto resDto = TagListResDto.of(tagList);
        return resDto;
    }
}
