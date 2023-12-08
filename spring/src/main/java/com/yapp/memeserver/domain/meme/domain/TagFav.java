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
@Table(name="TAG_FAV")
public class TagFav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_FAV_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "태그는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "TAG_ID", updatable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "계정은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "ACCOUNT_ID", updatable = false)
    private Account account;

    @Builder
    public TagFav(Tag tag, Account account) {
        this.tag = tag;
        this.account = account;
    }
}
