package com.facelink.service;

import com.facelink.dto.AccountDTO;
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

import java.util.ArrayList;
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

    public List<?> getAccountFriendDTO() {
        Long id = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        String avatar = "https://i0.wp.com/www.vidyadhirajamvk.org/wp-content/uploads/2022/08/venugopal.png?fit=436%2C534&ssl=1";
        Set<ListFriend> accounts = (Set<ListFriend>) this.getFriends(id);
        accounts.forEach(user -> {
            accountDTOS.add(AccountDTO.builder()
                    .id(user.getFriendInfo().getAccountInfo().getAccount().getId())
                    .fullName(user.getFriendInfo().getAccountInfo().getFullName())
                    .avatar(user.getFriendInfo().getAccountInfo().getAvatar() == null ? avatar : user.getFriendInfo().getAccountInfo().getAvatar()).build());
        });
        return accountDTOS;
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
