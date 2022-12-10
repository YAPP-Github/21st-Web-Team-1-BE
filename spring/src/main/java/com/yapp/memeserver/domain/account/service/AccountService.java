package com.yapp.memeserver.domain.account.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.dto.SignUpReqDto;
import com.yapp.memeserver.domain.account.dto.UpdateAccountReqDto;
import com.yapp.memeserver.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    // 조회시에 readOnly를 켜주면 flush가 일어나지 않아서 성능의 이점을 가질 수 있다.
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 Account 를 찾을 수 없습니다. id ="+id));
    }

    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 email 을 가진 Account 를 찾을 수 없습니다. id ="+email));
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Long signUp(SignUpReqDto requestDto) {
        if (isExistedEmail(requestDto.getEmail())) {
            throw new IllegalStateException(requestDto.getEmail());
        }

        String encodedPassword = encodePassword(requestDto.getPassword());
        Account account = accountRepository.save(requestDto.toEntity(encodedPassword));
        return account.getId();
    }

    // Account를 반환하게 되면, 변경하는 Command와 조회하는 Query가 분리되지 않는다.
    public void update(Long accountId, UpdateAccountReqDto requestDto) {
        Account account = findById(accountId);
        account.updateMyAccount(requestDto.getEmail(), requestDto.getName());
    }

    public void delete(Long accountId) {
        Account account = findById(accountId);
        accountRepository.delete(account);
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
