package com.facelink.service;

import com.facelink.dto.CustomUser;
import com.facelink.entity.Account;
import com.facelink.entity.AccountInfo;
import com.facelink.entity.ListFriend;
import com.facelink.repository.AccountInfoRepository;
import com.facelink.repository.ListFriendsRepository;
import com.facelink.repository.PostRepository;
import com.facelink.repository.RelationShipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfileService {
    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private ListFriendsRepository listFriendsRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RelationShipRepository relationShipRepository;

    public AccountInfo getInfo() {
        return this.accountInfoRepository.getBio(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    public Page<?> getPostsProfile(Long id, Pageable pageable) {
        return this.postRepository.getPostByAccount(id, pageable);
    }

    public ListFriend hasFriend(Long userId) {
        List<ListFriend> listFriend = this.listFriendsRepository.hasFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
        return listFriend.isEmpty() ? ListFriend.builder().status(-1).build() : listFriend.getFirst();
    }

    public Set<?> getFriends(Long id) {
        return this.listFriendsRepository.getFriendByUser(id);
    }

    @Transactional
    public void editDetails(AccountInfo accountInfo){
        Account account = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        accountInfo.setAccount(account);
        accountInfo.setBio(account.getAccountInfo().getBio());
        accountInfo.setCoverPhoto(account.getAccountInfo().getCoverPhoto());
        accountInfo.setAvatar(account.getAccountInfo().getAvatar());
        accountInfo.setFirstName(account.getAccountInfo().getFirstName());
        accountInfo.setLastName(account.getAccountInfo().getLastName());
        accountInfo.setFullName(account.getAccountInfo().getFullName());
        this.accountInfoRepository.save(accountInfo);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account accountContext = customUser.getAccount();
        accountContext.getAccountInfo().setOtherName(accountInfo.getOtherName());
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                customUser, customUser.getPassword(), customUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
    }

    public void updateBio(String bio){
        Account account = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateBio(account.getAccountInfo().getId(), bio);
    }

    public void updateAvatar(String url){
        Account a = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateAvatar(a.getId(), url);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = customUser.getAccount();
        account.getAccountInfo().setAvatar(url);
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                customUser, customUser.getPassword(), customUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
    }

    public void updateCoverPhoto(String url){
        Account a = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateCoverPhoto(a.getId(), url);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = customUser.getAccount();
        account.getAccountInfo().setCoverPhoto(url);
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                customUser, customUser.getPassword(), customUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
    }

    public List<?> getRelationships(){
        return this.relationShipRepository.findAll();
    }
}
