package com.yapp.memeserver.domain.meme.domain;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="COLLECTION")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLECTION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "회원은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "ACCOUNT_ID", updatable = false)
    private Account account;

    @Column(name = "IS_SHARED")
    private Boolean isShared;

    @Builder
    public Collection(Account account, Boolean isShared) {
        this.account = account;
        this.isShared = isShared;
    }
}
