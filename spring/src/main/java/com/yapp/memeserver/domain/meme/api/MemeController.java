package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.*;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.dto.MemeResDto;
import com.yapp.memeserver.domain.meme.dto.TagListResDto;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import com.yapp.memeserver.domain.meme.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
