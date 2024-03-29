package com.yapp.memeserver.domain.account.service;

import com.yapp.memeserver.domain.account.domain.Account;
import com.yapp.memeserver.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security에서 유저의 정보를 가져오는 인터페이스
// loginProcess.do 가 찾아오는 클래스다.
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository
            accountRepository;

    @Override
    // email 대신 PK 값을 넘겨줘도 된다. 다만 Long과 Sring을 반환하는 것이 귀찮고 예상치 못한 상황이 발생하기도 한다.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당 이메일을 가진 계정이 없습니다. email = " + email));

        // UserDetails를 반환한다.
        return new AccountAdapter(account);
    }
}