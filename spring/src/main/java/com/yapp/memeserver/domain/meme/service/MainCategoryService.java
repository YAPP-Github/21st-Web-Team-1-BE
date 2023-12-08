package com.yapp.memeserver.domain.meme.service;

import com.yapp.memeserver.domain.meme.domain.MainCategory;
import com.yapp.memeserver.domain.meme.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public List<MainCategory> findAllOrderByPriority() {
        return mainCategoryRepository.findAllByOrderByPriorityAsc();
    }

}