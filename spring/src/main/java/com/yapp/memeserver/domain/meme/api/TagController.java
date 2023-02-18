package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.service.AuthUser;
import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.*;
import com.yapp.memeserver.domain.meme.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final TagFavService tagFavService;

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public TagListResDto readAllTags(Pageable pageable) {
        List<Tag> tagList = tagService.findAllPaging(pageable);
        TagListResDto resDto = TagListResDto.of(tagList);
        return resDto;
    }

    @GetMapping("/{tagId}")
    @ResponseStatus(value = HttpStatus.OK)
    public TagResDto readTag(@PathVariable final Long tagId, @AuthUser Account account) {
        tagService.read(tagId);
        Tag tag = tagService.findById(tagId);
        // 인증이 안된 유저인 경우, account에 null이 들어간다.
        boolean isFav = tagFavService.isExistsByAccountAndTag(account, tag);

        TagResDto resDto = TagResDto.of(tag);
        resDto.setFav(isFav);

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
    public TagCategoryListResDto getTagCetegory(@AuthUser Account account) {
        List<Category> categoryList = categoryService.findAllOrderByPriority();
        TagCategoryListResDto resDto = TagCategoryListResDto.of(categoryList);

        resDto.getCategories().stream()
                .flatMap(c -> c.getTags().stream())
                .forEach(s -> s.setFav(tagFavService.isExistsByAccountAndTag(account, tagService.findById(s.getTagId()))));
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

    @PostMapping("{tagId}/fav")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void createTagFav(@PathVariable final Long tagId, @AuthUser Account account) {
        tagFavService.create(tagId, account);
    }

    @DeleteMapping("{tagId}/fav")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTagFav(@PathVariable final Long tagId, @AuthUser Account account) {
        tagFavService.delete(tagId, account);
    }
}
