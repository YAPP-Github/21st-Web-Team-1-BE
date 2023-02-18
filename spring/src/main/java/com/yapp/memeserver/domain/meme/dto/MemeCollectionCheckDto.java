package com.yapp.memeserver.domain.meme.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemeCollectionCheckDto {
    private Boolean isAdded;
    private Long collectionId;
}
