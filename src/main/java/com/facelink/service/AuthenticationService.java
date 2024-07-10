package com.facelink.service;

import com.facelink.entity.*;
import com.facelink.repository.AccountDetailRepository;
import com.facelink.repository.AccountInfoRepository;
import com.facelink.repository.AccountRepository;
import com.facelink.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDetailRepository accountDetailRepository;
    @Autowired
    private AccountInfoRepository accountInfoRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = this.accountRepository.save(account);
        AccountInfo accountInfo = AccountInfo.builder()
                .account(savedAccount)
                .firstName(account.getAccountInfo().getFirstName().trim())
                .lastName(account.getAccountInfo().getLastName().trim())
                .fullName(account.getAccountInfo().getLastName().trim() + " " + account.getAccountInfo().getFirstName().trim())
                .dateOfBirth(account.getAccountInfo().getDateOfBirth())
                .gender(account.getAccountInfo().getGender())
                .build();
        AccountDetail accountDetail = AccountDetail.builder()
                .account(savedAccount)
                .followers(0)
                .following(0)
                .build();
        this.accountDetailRepository.save(accountDetail);
        this.accountInfoRepository.save(accountInfo);
        savedAccount.setAccountInfo(accountInfo);
        savedAccount.setAccountDetails(accountDetail);
        this.userRoleRepository.save(UserRole.builder().role(Role.builder().id(2).build()).account(savedAccount).build());
        savedAccount = this.accountRepository.save(savedAccount);
        return savedAccount;
    }
}
