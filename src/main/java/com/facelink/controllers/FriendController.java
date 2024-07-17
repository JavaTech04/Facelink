package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.friendService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        }catch (Exception _){
            return null;
        }
    }

    @GetMapping
    public String friends(Model model) {
        model.addAttribute("friends", this.friendService.getListFriends());
        model.addAttribute("friendRequests", this.friendService.getFriendRequests());
        return "pages/friend/index";
    }

    @GetMapping("/unfriend/{id}")
    public String unfriend(@PathVariable("id") Long id) {
        this.friendService.unfriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/sendRequest/{id}")
    public String sendRequest(@PathVariable("id") Long id) {
        this.friendService.senRequestAddFriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/confirm/{id}")
    public String confirmFriend(@PathVariable("id") Long id) {
        this.friendService.confirmFriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/cancel/{id}")
    public String cancelFriend(@PathVariable("id") Long id) {
        this.friendService.cancelFriend(id);
        return "redirect:/profile/" + id;
    }
}
