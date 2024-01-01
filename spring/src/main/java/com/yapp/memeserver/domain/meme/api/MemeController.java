package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.*;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.dto.MemeResDto;
import com.yapp.memeserver.domain.meme.dto.TagListResDto;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import com.yapp.memeserver.domain.meme.service.*;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.service.AccountService;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.*;
import com.yapp.memeserver.domain.meme.service.ImageService;
import com.yapp.memeserver.domain.meme.service.MemeService;
import com.yapp.memeserver.domain.meme.service.MemeTagService;
import com.yapp.memeserver.domain.meme.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/memes")
@RequiredArgsConstructor
public class MemeController {

    private final MemeService memeService;
    private final TagService tagService;
    private final CollectionService collectionService;
    private final MemeTagService memeTagService;
    private final MemeCollectionService memeCollectionService;

    private final ImageService imageService;
    private final AccountService accountService;


    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public MemeListResDto readAllMemes(Pageable pageable) {
        List<Meme> memeList = memeService.findAllPaging(pageable);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }

    @GetMapping("/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemeResDto readMeme(@PathVariable final Long memeId) {
        memeService.read(memeId);
        Meme meme = memeService.findById(memeId);
        MemeResDto resDto = MemeResDto.of(meme);
        return resDto;
    }

    @GetMapping("/tags/{tagId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemeListResDto getMemeTag(@PathVariable final Long tagId, Pageable pageable) {
        Tag tag = tagService.findById(tagId);
        Page<MemeTag> memeTagList = memeTagService.findByTagPaging(tag, pageable);
        List<Meme> memeList = memeTagService.findMemeTagList(memeTagList);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }


    @GetMapping("/collections/{collectionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemeListResDto getMemeCollection(@PathVariable final Long collectionId, Pageable pageable) {
        Collection collection = collectionService.findById(collectionId);
        Page<MemeCollection> memeCollectionList = memeCollectionService.findByCollectionPaging(collection, pageable);
        List<Meme> memeList = memeCollectionService.findMemeCollectionList(memeCollectionList);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }


    @PostMapping(value = "/{accountId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MemeDetailResDto createMeme(@PathVariable final Long accountId,
                                       @RequestBody final MemeCreateReqDto reqDto) throws IOException {

        Account account = accountService.findById(accountId);
        Meme meme = memeService.create(reqDto.getName(), reqDto.getDescription(), account, MemeStatus.DRAFT);

        imageService.setMemeImage(meme, reqDto.getImages());

        List<Tag> tagList = tagService.findTagList(reqDto.getTags());
        List<MemeCreateReqDto.NewSingleTag> newTags = reqDto.getNewTags();

        if (newTags != null && !newTags.isEmpty()) {
            List<Tag> newTagList = tagService.createTagList(reqDto.getNewTags());
            tagList.addAll(newTagList);
        }

        for (Tag tag : tagList) {
            memeTagService.create(meme, tag);
        }

        MemeDetailResDto resDto = MemeDetailResDto.of(meme, tagList);
        return resDto;
    }

    @GetMapping("/{memeId}/rel")
    @ResponseStatus(value = HttpStatus.OK)
    public MemeListResDto readRelMemes(@PathVariable final Long memeId, Pageable pageable) {
        memeService.read(memeId);
        Meme meme = memeService.findById(memeId);
        List<MemeTag> memeTagList = memeTagService.findByMeme(meme);
        List<Long> tagIdList = memeTagService.findTagIdList(memeTagList);
        Page<MemeTag> memeTagPage = memeTagService.findByRelTagPaging(memeId, tagIdList, pageable);
        List<Meme> memeList = memeTagService.findMemeTagList(memeTagPage);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }
}
