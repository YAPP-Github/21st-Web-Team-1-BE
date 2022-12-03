package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.dto.MemeResDto;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import com.yapp.memeserver.domain.meme.service.MemeService;
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
        Meme meme = memeService.findById(memeId);
        meme.updateViewCount();
        MemeResDto resDto = MemeResDto.of(meme);
        return resDto;
    }


}
