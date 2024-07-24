package com.facelink.service;

import com.facelink.dto.AccountFullName;
import com.facelink.dto.AccountLoginChange;
import com.facelink.entity.Account;
import com.facelink.entity.AccountDetail;
import com.facelink.entity.ChatRoom;
import com.facelink.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired private AccountInfoRepository accountInfoRepository;
    @Autowired private AccountRepository accountRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired private ReactionRepository reactionRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private ListFriendsRepository listFriendsRepository;
    @Autowired private ChatRoomRepository chatRoomRepository;
    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private AccountDetailRepository accountDetailRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void updateInfo(AccountFullName accountFullName) {
        this.accountInfoRepository.updateName(
                accountFullName.getId(),
                accountFullName.getFirstName(),
                accountFullName.getLastName(),
                accountFullName.getLastName() + " " + accountFullName.getFirstName()
        );
    }

    @Transactional
    public void updateLogin(AccountLoginChange accountLoginChange) {
        if (accountLoginChange.getPassword().isBlank()) {
            Account account = this.accountRepository.findById(accountLoginChange.getId()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
            accountLoginChange.setPassword(account.getPassword());
        } else {
            accountLoginChange.setPassword(this.passwordEncoder.encode(accountLoginChange.getPassword()));
        }
        this.accountRepository.updateAccountLogin(
                accountLoginChange.getEmail(),
                accountLoginChange.getPhoneNumber(),
                accountLoginChange.getPassword(),
                accountLoginChange.getId()
        );
    }

    @Transactional
    public void deleteAccount(Long id){
        this.userRoleRepository.deleteAccountMain(id);
        this.reactionRepository.deleteAccountMain(id);
        this.commentRepository.deleteAccountMain(id);
        this.postRepository.deleteAccountMain(id);
        this.listFriendsRepository.deleteAccountMain(id);
        this.chatRoomRepository.deleteAccountMain(id);
        this.chatMessageRepository.deleteAccountMain(id);
        this.accountInfoRepository.deleteAccountMain(id);
        this.accountDetailRepository.deleteAccountMain(id);
        this.accountRepository.deleteAccountMain(id);
    }
}
