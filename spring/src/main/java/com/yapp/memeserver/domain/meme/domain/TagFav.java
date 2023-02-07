package com.yapp.memeserver.domain.meme.domain;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagFav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_fav_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "태그는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "tag_id", updatable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "계정은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

}
