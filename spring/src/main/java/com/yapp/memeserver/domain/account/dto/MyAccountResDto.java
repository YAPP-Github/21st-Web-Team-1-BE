package com.yapp.memeserver.domain.account.dto;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyAccountResDto {

    private String email;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MyAccountResDto(Account account) {
        this.email = account.getEmail();
        this.name = account.getName();
        this.createdDate = account.getCreatedDate();
        this.modifiedDate = account.getModifiedDate();
    }
}
