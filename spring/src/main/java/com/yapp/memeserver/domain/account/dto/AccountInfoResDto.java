package com.yapp.memeserver.domain.account.dto;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResDto {

    private String email;
    private String name;

    public static AccountInfoResDto of(Account account) {
        return AccountInfoResDto.builder()
                .email(account.getEmail())
                .name(account.getName())
                .build();
    }
}
