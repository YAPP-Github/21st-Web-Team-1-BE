package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeStatus;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.MemeCreateReqDto;
import com.yapp.memeserver.domain.meme.dto.MemeListResDto;
import com.yapp.memeserver.domain.meme.repository.MemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemeService {

    private final MemeRepository memeRepository;

    @Transactional(readOnly = true)
    public Meme findById(Long memeId) {
        return memeRepository.findById(memeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Meme을 찾을 수 없습니다. id = "+ memeId ));
    }

    public List<Meme> findAllPaging(Pageable pageable) {
        Page<Meme> memePage = memeRepository.findAll(pageable);
        List<Meme> memeList = new ArrayList<Meme>();

        if(memePage!=null && memePage.hasContent()){
            memeList = memePage.getContent();
        }
        return memeList;
    }

    public void read(Long memeId) {
        memeRepository.increaseViewCount(memeId);
    }

    public Meme create(String name, String description, Account account, MemeStatus status) {
        Meme meme = Meme.builder()
                .name(name)
                .description(description)
                .writer(account)
                .status(status)
                .build();
        return memeRepository.save(meme);
    }
}
