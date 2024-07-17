package com.facelink.service;

import com.facelink.dto.CustomUser;
import com.facelink.entity.ListFriend;
import com.facelink.repository.AccountInfoRepository;
import com.facelink.repository.AccountRepository;
import com.facelink.repository.ListFriendsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FriendService {
    @Autowired
    private ListFriendsRepository listFriendsRepository;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Set<?> getFriends(Long id) {
        return this.listFriendsRepository.getFriendByUser(id);
    }
    public List<?> getListFriends() {
        return this.accountInfoRepository.getAccountInfoByAccountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    public List<?> getFriendRequests() {
        return this.listFriendsRepository.friendRequests(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    @Transactional
    public void unfriend(Long userId) {
        this.listFriendsRepository.removeFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
    }

    @Transactional
    public void senRequestAddFriend(Long userId) {
        this.listFriendsRepository.save(ListFriend.builder()
                .accountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())
                .friendInfo(this.accountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found account!!")))
                .status(0)
                .build());
    }

    @Transactional
    public void confirmFriend(Long userId) {
        this.listFriendsRepository.updateStatusFriend(userId);
        this.listFriendsRepository.save(ListFriend.builder()
                .accountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())
                .friendInfo(this.accountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found account!!")))
                .status(1)
                .build());
    }

    @Transactional
    public void cancelFriend(Long userId) {
        this.listFriendsRepository.removeFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
    }
}
