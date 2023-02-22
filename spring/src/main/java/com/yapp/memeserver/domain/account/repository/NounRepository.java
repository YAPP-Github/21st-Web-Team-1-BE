package com.yapp.memeserver.domain.account.repository;

import com.yapp.memeserver.domain.account.domain.Noun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NounRepository extends JpaRepository<Noun, Long> {
    long count();
}
