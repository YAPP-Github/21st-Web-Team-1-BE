package com.yapp.memeserver.domain.account.repository;

import com.yapp.memeserver.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.saveCount = a.saveCount + :val WHERE a.id = :accountId")
    void updateSaveCount(@Param("accountId") Long accountId, @Param("val") Integer val);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.shareCount = a.shareCount + :val WHERE a.id = :accountId")
    void updateShareCount(@Param("accountId") Long accountId, @Param("val") Integer val);
}
