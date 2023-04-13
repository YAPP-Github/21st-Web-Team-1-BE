package com.yapp.memeserver.domain.meme.api;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.auth.service.AuthUser;
import com.yapp.memeserver.domain.meme.domain.Collection;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.dto.MemeCollectionCheckDto;
import com.yapp.memeserver.domain.meme.service.CollectionService;
import com.yapp.memeserver.domain.meme.service.MemeCollectionService;
import com.yapp.memeserver.domain.meme.service.MemeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final MemeService memeService;
    private final CollectionService collectionService;
    private final MemeCollectionService memeCollectionService;

    @GetMapping("/check/memes/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemeCollectionCheckDto checkMemeCollection(@PathVariable Long memeId, @AuthUser Account account) {
        Meme meme = memeService.findById(memeId);
        Collection collection = collectionService.findCollection(account);
        boolean existsByMemeAndCollection = memeCollectionService.isExistsByMemeAndCollection(meme, collection);
        MemeCollectionCheckDto resDto = MemeCollectionCheckDto.builder()
                .collectionId(collection.getId())
                .isAdded(existsByMemeAndCollection)
                .build();
        return resDto;
    }

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

    @PostMapping("/share/memes/{memeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void createMemeShareCollection(@PathVariable Long memeId, @AuthUser Account account) {
        Meme meme = memeService.findById(memeId);
        memeCollectionService.createShare(meme, account);
    }
}
