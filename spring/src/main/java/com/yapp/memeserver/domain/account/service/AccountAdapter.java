package com.yapp.memeserver.domain.account.service;

import com.yapp.memeserver.domain.account.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AccountAdapter extends User {

    private Account account;

    public AccountAdapter(Account account) {
        // Account에 Role 추가 후에 변경할 것
        super(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

}