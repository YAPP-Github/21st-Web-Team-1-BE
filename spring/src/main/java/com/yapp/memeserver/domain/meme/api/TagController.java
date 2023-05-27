package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.service.AuthUser;
import com.yapp.memeserver.domain.meme.domain.*;
import com.yapp.memeserver.domain.meme.dto.*;
import com.yapp.memeserver.domain.meme.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final CategoryService categoryService;
    private final MainCategoryService mainCategoryService;
    private final MemeService memeService;
    private final MemeTagService memeTagService;
    private final TagFavService tagFavService;
    private final ImageService imageService;

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
    public TagListResDto searchTag(@RequestParam String word) {
        List<Tag> tagList = tagService.findByNameContains(word);
        TagListResDto resDto = TagListResDto.of(tagList);
        return resDto;
    }

    @GetMapping("/categories/new")
    @ResponseStatus(value = HttpStatus.OK)
    public MainCategoryListResDto getTagCategory() {
        List<MainCategory> mainCategoryList = mainCategoryService.findAllOrderByPriority();
        List<List<Tag>> tagListList = new ArrayList<>();
        HashMap<Long, HashMap<Category, List<Tag>>> mainCategoryMap = new HashMap<>();

        for(int i = 0; i < mainCategoryList.size(); i++) {
            MainCategory mainCategory = mainCategoryList.get(i);
            HashMap<Category, List<Tag>> categoryMap = categoryService.getCategoryMap(mainCategory);
            mainCategoryMap.put(mainCategory.getId(), categoryMap);

            if (i == 0) {
                tagListList.add(categoryService.getCarouselUser(categoryMap));
            }
            if (i == 1 || i == 2) {
                tagListList.add(categoryService.getCarouselEmotion(categoryMap, i + 2));
            }
        }

        MainCategoryListResDto resDto = MainCategoryListResDto.of(mainCategoryList, mainCategoryMap, tagListList);
        return resDto;
    }

    @GetMapping("/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public TagCategoryListResDto getTagCategory(@AuthUser Account account) {
        List<Category> categoryList = categoryService.findAllOrderByPriority();
        List<Long> favTagIdList = tagFavService.getFavTagIdList(account);
        TagCategoryListResDto resDto = TagCategoryListResDto.of(categoryList, favTagIdList);

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

    @GetMapping("/favs")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public SingleTagFavListResDto readTagFav(@AuthUser Account account) {
        List<Tag> tagList = tagFavService.read(account);
        SingleTagFavListResDto resDto = SingleTagFavListResDto.of(tagList);
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

    @GetMapping("/rank/new")
    @ResponseStatus(value = HttpStatus.OK)
    public TagRankListResDto getTagRank() {
        List<Tag> tagList = tagService.findTop10ByOrderByViewCountDesc();
        List<String> imageList = imageService.getTagRankImageLists(tagList);
        TagRankListResDto resDto = TagRankListResDto.of(tagList, imageList);
        return resDto;
    }
}
