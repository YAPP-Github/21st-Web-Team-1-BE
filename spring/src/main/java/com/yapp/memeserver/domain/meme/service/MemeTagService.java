package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.MemeTag;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.MemeTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    public Page<MemeTag> findByTagPaging(Tag tag, Pageable pageable) {
        return memeTagRepository.findByTag(tag, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MemeTag> findByRelTagPaging(Long memeId, List<Long> tagIdList, Pageable pageable) {
        return memeTagRepository.findByRelTag(memeId, tagIdList, pageable);
    }


    @Transactional(readOnly = true)
    public List<Tag> findTagMemeList(List<MemeTag> memeTagList) {
        return memeTagList.stream()
                .map(MemeTag::getTag)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Long> findTagIdList(List<MemeTag> memeTagList) {
        return memeTagList.stream()
                .map(m -> m.getTag().getId())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Meme> findMemeTagList(Page<MemeTag> memeTagPage) {
        return memeTagPage.stream()
                .map(MemeTag::getMeme)
                .collect(Collectors.toList());
    }
}
