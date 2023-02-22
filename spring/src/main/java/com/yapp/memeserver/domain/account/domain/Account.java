package com.yapp.memeserver.domain.account.domain;


import com.yapp.memeserver.domain.meme.domain.Collection;
import com.yapp.memeserver.domain.meme.domain.Tag;
import com.yapp.memeserver.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="ACCOUNT")
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 컬럼명은 따로 지정하는 것이 더 명확하다.
    @Column(name = "ACCOUNT_ID", updatable = false)
    private Long id;

    @NotNull(message = "이메일은 필수로 입력되어야 합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Column(name = "NAME")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull(message = "비밀번호는 필수로 입력되어야 합니다.")
    @Column(name = "PASSWORD")
    private String password;

    @URL
    @Size(max = 2048)
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collection> collectionList = new ArrayList<>();

    @Column(name = "SAVE_COUNT")
    private Integer saveCount;

    @Column(name = "SHARE_COUNT")
    private Integer shareCount;

    // 연관관계 편의 메소드
    public void addCollection(Collection collection) {
        collectionList.add(collection);
    }

    @Builder
    public Account(String email, String name, String password, String imageUrl) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.saveCount = 0;
        this.shareCount = 0;
        this.imageUrl = imageUrl;
    }

    public void updateMyAccount(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}