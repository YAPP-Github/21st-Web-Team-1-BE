package com.yapp.memeserver.domain.account.domain;


import com.yapp.memeserver.domain.meme.domain.Collection;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 컬럼명은 따로 지정하는 것이 더 명확하다.
    @Column(name = "account_id", updatable = false)
    private Long id;

    @NotNull(message = "이메일은 필수로 입력되어야 합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    private String name;

    @NotNull(message = "비밀번호는 필수로 입력되어야 합니다.")
    private String encodedPassword;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collection> collectionList = new ArrayList<>();

    private Integer saveCount;

    private Integer shareCount;

    // 연관관계 편의 메소드
    public void addCollection(Collection collection) {
        collectionList.add(collection);
    }

    @Builder
    public Account(String email, String name, String encodedPassword) {
        this.email = email;
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.saveCount = 0;
        this.shareCount = 0;
    }

    public void updateMyAccount(String email, String name, String encodedPassword) {
        this.email = email;
        this.name = name;
        this.encodedPassword = encodedPassword;
    }
}