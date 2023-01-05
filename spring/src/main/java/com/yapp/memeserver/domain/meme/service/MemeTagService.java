package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.MemeTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemeTagService {

    private final MemeTagRepository memeTagRepository;

    @Transactional(readOnly = true)
    public List<MemeTag> findByMeme(Meme meme) {
        return memeTagRepository.findByMeme(meme);
    }

    @Transactional(readOnly = true)
    public List<Tag> findTagMemeList(List<MemeTag> memeTagList) {
        return memeTagList.stream()
                .map(MemeTag::getTag)
                .collect(Collectors.toList());
    }
}
