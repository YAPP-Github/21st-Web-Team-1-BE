package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.domain.meme.domain.TagFav;
import com.yapp.memeserver.domain.meme.repository.TagFavRepository;
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
public class TagFavService {

    private final TagFavRepository tagFavRepository;
    private final TagService tagService;

    @Transactional(readOnly = true)
    public boolean isExistsByAccountAndTag(Account account, Tag tag) {
        return tagFavRepository.existsByAccountAndTag(account, tag);
    }

    @Transactional(readOnly = true)
    public Integer countTagFav(Tag tag) {
        Integer count = tagFavRepository.countByTag(tag);
        return count;
    }

    @Transactional(readOnly = true)
    public List<TagFav> findByAccount(Account account) {
        return tagFavRepository.findByAccount(account);
    }

    @Transactional(readOnly = true)
    public List<Tag> findFavTagList(List<TagFav> tagFavList) {
        return tagFavList.stream()
                .map(TagFav::getTag)
                .collect(Collectors.toList());
    }

    public void create(Long tagId, Account account) {
        Tag tag = tagService.findById(tagId);
        if (isExistsByAccountAndTag(account, tag)) {
            throw new RuntimeException("이미 즐겨찾기가 처리되었습니다.");
        }
        TagFav tagFav = TagFav.builder()
                .tag(tag)
                .account(account)
                .build();
        tagFavRepository.save(tagFav);
    }

    public void delete(Long tagId, Account account) {
        Tag tag = tagService.findById(tagId);

        TagFav tagFav = tagFavRepository.findByAccountAndTag(account, tag)
                .orElseThrow(() -> new RuntimeException("즐겨찾기가 존재하지 않습니다."));
        tagFavRepository.delete(tagFav);
    }
}
