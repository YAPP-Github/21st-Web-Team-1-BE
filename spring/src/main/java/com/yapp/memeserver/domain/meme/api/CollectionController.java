package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.service.AuthUser;
import com.yapp.memeserver.domain.meme.domain.Collection;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.dto.TagCategoryListResDto;
import com.yapp.memeserver.domain.meme.service.CollectionService;
import com.yapp.memeserver.domain.meme.service.MemeCollectionService;
import com.yapp.memeserver.domain.meme.service.MemeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final MemeService memeService;
    private final CollectionService collectionService;
    private final MemeCollectionService memeCollectionService;

    @PostMapping("/memes/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void createMemeCollection(@PathVariable Long memeId, @AuthUser Account account) {
        Meme meme = memeService.findById(memeId);

        memeCollectionService.create(meme, account);
    }

    @DeleteMapping("/memes/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMemeCollection(@PathVariable Long memeId, @AuthUser Account account) {
        Meme meme = memeService.findById(memeId);

        memeCollectionService.delete(meme, account);
    }
}
