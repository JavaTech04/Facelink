package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.entity.AccountInfo;
import com.facelink.service.AuthenticationService;
import com.facelink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class AppController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.userService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        }catch (Exception _){
            return null;
        }
    }

    @ModelAttribute("relationships")
    public List<?> getRelationships(){
        return this.userService.getRelationships();
    }

    @GetMapping
    public String index() {
        return "user/home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("info", this.userService.getInfo());
        model.addAttribute("accountInfo", ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getAccountInfo());
        return "user/profile";
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hasFriend", this.userService.hasFriend(id));
        model.addAttribute("listFriendsViewProfile", this.userService.getFriends(id));
        model.addAttribute("info", this.authenticationService.findAccountById(id));
        return "user/viewProfile";
    }

    @PostMapping("/profile/update")
    public String editDetails(@ModelAttribute("accountInfo") AccountInfo accountInfo){
        this.userService.editDetails(accountInfo);
        return "redirect:/profile";
    }
    @PostMapping("/profile/updateBio")
    public String updateBio(@RequestParam("bio") String bio){
        this.userService.updateBio(bio);
        return "redirect:/profile";
    }

    @GetMapping("/friends")
    public String friends(Model model) {
        model.addAttribute("friends", this.userService.getListFriends());
        model.addAttribute("friendRequests", this.userService.getFriendRequests());
        return "user/friend";
    }

    @GetMapping("/friends/unfriend/{id}")
    public String unfriend(@PathVariable("id") Long id) {
        this.userService.unfriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/friends/sendRequest/{id}")
    public String sendRequest(@PathVariable("id") Long id) {
        this.userService.senRequestAddFriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/friends/confirm/{id}")
    public String confirmFriend(@PathVariable("id") Long id) {
        this.userService.confirmFriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/friends/cancel/{id}")
    public String cancelFriend(@PathVariable("id") Long id) {
        this.userService.cancelFriend(id);
        return "redirect:/profile/" + id;
    }

    @GetMapping("/videos")
    public String videos() {
        return "user/coming-soon";
    }

    @GetMapping("/marketplace")
    public String marketplace() {
        return "user/coming-soon";
    }

    @GetMapping("/groups")
    public String groups() {
        return "user/coming-soon";
    }

    @GetMapping("/demo")
    public String demo() {
        return "user/demo";
    }
}
