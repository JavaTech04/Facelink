package com.facelink.service;

import com.facelink.dto.CustomUser;
import com.facelink.entity.Account;
import com.facelink.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findAccount(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return CustomUser.builder().account(account).build();
    }
}
