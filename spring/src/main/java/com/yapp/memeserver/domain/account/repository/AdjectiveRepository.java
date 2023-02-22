package com.yapp.memeserver.domain.account.repository;

import com.yapp.memeserver.domain.account.domain.Adjective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjectiveRepository extends JpaRepository<Adjective, Long> {
    long count();
}
