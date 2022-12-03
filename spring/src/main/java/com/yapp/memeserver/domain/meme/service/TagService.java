package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
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
public class TagService {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Tag 을 찾을 수 없습니다. id = "+ tagId ));
    }

    public List<Tag> findAllPaging(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        List<Tag> tagList = new ArrayList<Tag>();

        if(tagPage!=null && tagPage.hasContent()){
            tagList = tagPage.getContent();
        }
        return tagList;
    }

    public void read(Long tagId) {
        tagRepository.updateViewCount(tagId);
    }



}
