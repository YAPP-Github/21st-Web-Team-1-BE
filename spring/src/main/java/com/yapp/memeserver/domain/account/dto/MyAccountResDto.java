package com.yapp.memeserver.domain.account.dto;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.meme.domain.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyAccountResDto {

    private String email;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer saveCount;
    private Integer shareCount;
    private Long collectionId;
    private Long sharedCollectionId;

    public MyAccountResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
        this.createdDate = account.getCreatedDate();
        this.modifiedDate = account.getModifiedDate();
        this.saveCount = account.getSaveCount();
        this.shareCount = account.getShareCount();
    }
    public void setCollectionInfo(Collection collection, Collection sharedCollection) {
        this.collectionId = collection.getId();
        this.sharedCollectionId = sharedCollection.getId();
    }
}
