package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.dto.MemeResDto;
import com.yapp.memeserver.domain.meme.dto.TagListResDto;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
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
@RequestMapping("/memes")
@RequiredArgsConstructor
public class MemeController {

    private final MemeService memeService;
    private final TagService tagService;
    private final MemeTagService memeTagService;

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public MemeListResDto readAllMemes(Pageable pageable) {
        List<Meme> memeList = memeService.findAllPaging(pageable);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }

    @CrossOrigin(origins = "*")
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
    public MemeListResDto getMemeTag(@PathVariable final Long tagId) {
        Tag tag = tagService.findById(tagId);
        List<MemeTag> memeTagList = memeTagService.findByTag(tag);
        List<Meme> memeList = memeTagService.findMemeTagList(memeTagList);
        MemeListResDto resDto = MemeListResDto.of(memeList);
        return resDto;
    }


}
