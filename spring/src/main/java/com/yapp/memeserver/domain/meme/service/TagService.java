package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.Category;
import com.yapp.memeserver.domain.meme.domain.Meme;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.dto.MemeCreateReqDto;
import com.yapp.memeserver.domain.meme.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Tag 을 찾을 수 없습니다. id = "+ tagId ));
    }

    @Transactional(readOnly = true)
    public List<Tag> findByCategory(Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return tagRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Tag> findAllPaging(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        List<Tag> tagList = new ArrayList<Tag>();

        if(tagPage!=null && tagPage.hasContent()){
            tagList = tagPage.getContent();
        }
        return tagList;
    }

    @Transactional(readOnly = true)
    public List<Tag> findByNameContains(String word) {
        if (word == null || word.length() < 2) {
            // NPE를 방지하기 위해 null 보다 빈 리스트를 반환하는 것이 좋다.
            // ArrayList<Tag>(); 보다 아래의 방식이 shared, immutable 객체를 반환하므로 메모리 차원에서 효율적이다.
            return Collections.emptyList();
        }
        List<Tag> tagList = tagRepository.findByNameContainsOrderByViewCountDesc(word);
        return tagList;
    }

    public void read(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("해당 id를 가진 Tag 을 찾을 수 없습니다. id = "+ tagId );
        }
        tagRepository.increaseViewCount(tagId);
    }


    @Transactional(readOnly = true)
    public List<Tag> findTop10ByOrderByViewCountDesc() {
        return tagRepository.findTop10ByOrderByViewCountDesc();
    }


    public Tag createTag(String name, Long categoryId) {
        Category category = categoryService.findById(categoryId);
        Tag tag = Tag.builder()
                .name(name)
                .category(category)
                .build();
        return tagRepository.save(tag);
    }

    public List<Tag> createTagList(List<MemeCreateReqDto.NewSingleTag> newTagList) {
        List<Tag> tagList = new ArrayList<>();
        for(MemeCreateReqDto.NewSingleTag newSingleTag : newTagList){
            Tag tag = createTag(newSingleTag.getName(), newSingleTag.getCategoryId());
            tagList.add(tag);
        }
        return tagList;
    }

    public List<Tag> findTagList(List<MemeCreateReqDto.SingleTag> tags) {
        List<Tag> tagList = new ArrayList<>();
        for(MemeCreateReqDto.SingleTag singleTag : tags){
            Tag tag = findById(singleTag.getTagId());
            tagList.add(tag);
        }
        return tagList;
    }
}
