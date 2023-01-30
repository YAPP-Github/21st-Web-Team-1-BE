package com.yapp.memeserver.domain.account.api;


import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.dto.MyAccountResDto;
import com.yapp.memeserver.domain.account.dto.SignUpReqDto;
import com.yapp.memeserver.domain.account.dto.UpdateAccountReqDto;
import com.yapp.memeserver.domain.account.service.AccountService;
import com.yapp.memeserver.domain.auth.service.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MyAccountResDto signUp(@RequestBody @Valid final SignUpReqDto requestDto) {
        Long accountId = accountService.signUp(requestDto);
        Account findAccount = accountService.findById(accountId);
        return new MyAccountResDto(findAccount);
    }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public MyAccountResDto getMyAccount(@AuthUser Account account) {
        log.info(account.getEmail());
        System.out.println("account = " + account);
        return new MyAccountResDto(account);
    }

    @PutMapping("/{accountId}")
    public MyAccountResDto updateMyAccount(@PathVariable final Long accountId,
                                           @RequestBody final UpdateAccountReqDto requestDto) {
        accountService.update(accountId, requestDto);
        Account account = accountService.findById(accountId);
        return new MyAccountResDto(account);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMyAccount(@PathVariable final Long accountId) {
        accountService.delete(accountId);
    }
}
