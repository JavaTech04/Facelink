package com.facelink.service;

import com.facelink.entity.Account;
import com.facelink.entity.UserRole;
import com.facelink.repository.AccountRepository;
import com.facelink.repository.RoleRepository;
import com.facelink.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SettingService settingService;

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public void lockAccount(Long id) {
        this.accountRepository.lockAccount(id);
    }

    public void unlockAccount(Long id) {
        this.accountRepository.unLockAccount(id);
    }

    public void verifiedAccount(Long id) {
        this.accountRepository.verifiedAccount(id);
    }

    public void unVerifiedAccount(Long id) {
        this.accountRepository.unVerifiedAccount(id);
    }

    public void setAdmin(Long id) {
        this.userRoleRepository.save(UserRole.builder()
                .role(this.roleRepository.findByName("ADMIN"))
                .account(this.accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Not found!")))
                .build());
    }

    public void removeAdmin(Long id) {
        this.userRoleRepository.removeAdmin(id);
    }

    public void deleteAccount(Long id) {
        this.settingService.deleteAccount(id);
    }
}
