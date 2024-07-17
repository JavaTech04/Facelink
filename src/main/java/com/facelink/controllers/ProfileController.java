package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.entity.AccountInfo;
import com.facelink.service.AuthenticationService;
import com.facelink.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private AuthenticationService authenticationService;

    //Required
    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.profileService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        } catch (Exception _) {
            return null;
        }
    }

    public void modelProfile(Model model) {
        model.addAttribute("info", this.profileService.getInfo());
        model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId()));
        model.addAttribute("accountInfo", ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getAccountInfo());
    }

    public void modelProfileOther(Model model, Long id) {
        model.addAttribute("hasFriend", this.profileService.hasFriend(id));
        model.addAttribute("listFriendsViewProfile", this.profileService.getFriends(id));
        model.addAttribute("info", this.authenticationService.findAccountById(id));
        model.addAttribute("listPost", this.profileService.getPostsProfile(id));
    }

    @ModelAttribute("relationships")
    public List<?> getRelationships() {
        return this.profileService.getRelationships();
    }

    @GetMapping
    public String profile(Model model) {
        this.modelProfile(model);
        return "pages/profile/myProfile/index";
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        if (Objects.equals(id, ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())) {
            return "redirect:/profile";
        }
        this.modelProfileOther(model, id);
        return "pages/profile/otherProfile/index";
    }

    @PostMapping("/update")
    public String editDetails(@ModelAttribute("accountInfo") AccountInfo accountInfo) {
        this.profileService.editDetails(accountInfo);
        return "redirect:/profile";
    }

    @PostMapping("/updateBio")
    public String updateBio(@RequestParam("bio") String bio) {
        this.profileService.updateBio(bio);
        return "redirect:/profile";
    }

    @PostMapping("/updateAvatar")
    public String updateAvatar(@RequestParam("avatar") String avatar) {
        this.profileService.updateAvatar(avatar);
        return "redirect:/profile";
    }

    @PostMapping("/updateCoverPhoto")
    public String updateCoverPhoto(@RequestParam("coverPhoto") String coverPhoto) {
        this.profileService.updateCoverPhoto(coverPhoto);
        return "redirect:/profile";
    }

    @GetMapping("/loadSection")
    public String loadSection(@RequestParam String section, Model model) {
        return switch (section) {
            case "about" -> "layout/profile/myProfile/_about";
            case "friends" -> "layout/profile/myProfile/_friends";
            case "photos" -> {
                model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId()));
                yield "layout/profile/myProfile/_photos";
            }
            case "videos" -> {
                model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId()));
                yield "layout/profile/myProfile/_videos";
            }
            case "reels" -> "layout/profile/myProfile/_reels";
            default -> {
                this.modelProfile(model);
                yield "layout/profile/myProfile/_posts";
            }
        };
    }

    @GetMapping("/{id}/loadSection")
    public String loadSectionView(@PathVariable("id") Long id, @RequestParam String section, Model model) {
        return switch (section) {
            case "about" -> "layout/profile/otherProfile/_about";
            case "friends" -> "layout/profile/otherProfile/_friends";
            case "photos" -> {
                model.addAttribute("info", this.authenticationService.findAccountById(id));
                model.addAttribute("listPost", this.profileService.getPostsProfile(id));
                yield "layout/profile/otherProfile/_photos";
            }
            case "videos" -> {
                model.addAttribute("info", this.authenticationService.findAccountById(id));
                model.addAttribute("listPost", this.profileService.getPostsProfile(id));
                yield "layout/profile/otherProfile/_videos";
            }
            case "reels" -> "layout/profile/otherProfile/_reels";
            default -> {
//                this.modelProfile(model);
                this.modelProfileOther(model, id);
                yield "layout/profile/otherProfile/_posts";
            }
        };
    }
}
