package com.facelink.service;

import com.facelink.dto.CustomUser;
import com.facelink.repository.ListFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private ListFriendsRepository listFriendsRepository;

    public Set<?> getFriends() {
        return this.listFriendsRepository.getFriendByUser( ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }
}
