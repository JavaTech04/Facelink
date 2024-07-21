package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.entity.AccountInfo;
import com.facelink.service.AppService;
import com.facelink.service.AuthenticationService;
import com.facelink.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AppService appService;

    //Required
    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.profileService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        } catch (Exception e) {
            return null;
        }
    }

    public void modelProfile(Model model, Pageable pageable) {
        model.addAttribute("info", this.profileService.getInfo());
        model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), pageable));
        model.addAttribute("accountInfo", ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getAccountInfo());
    }

    public void modelProfileOther(Model model, Long id, Pageable pageable) {
        model.addAttribute("hasFriend", this.profileService.hasFriend(id));
        model.addAttribute("listFriendsViewProfile", this.profileService.getFriends(id));
        model.addAttribute("info", this.authenticationService.findAccountById(id));
        model.addAttribute("listPost", this.profileService.getPostsProfile(id, pageable));
    }

    @ModelAttribute("relationships")
    public List<?> getRelationships() {
        return this.profileService.getRelationships();
    }

    @GetMapping
    public String profile(Model model, @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 7);
        this.modelProfile(model, pageable);
        return "pages/profile/myProfile/index";
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model, @RequestParam("p") Optional<Integer> p) {
        if (Objects.equals(id, ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())) {
            return "redirect:/profile";
        }
        Pageable pageable = PageRequest.of(p.orElse(0), 7);
        this.modelProfileOther(model, id, pageable);
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
        Pageable pageable = PageRequest.of(0, 7);
        return switch (section) {
            case "about" -> {
                this.modelProfile(model, pageable);
                yield "layout/profile/myProfile/_about";
            }
            case "friends" -> "layout/profile/myProfile/_friends";
            case "photos" -> {
                model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), pageable));
                yield "layout/profile/myProfile/_photos";
            }
            case "videos" -> {
                model.addAttribute("listPost", this.profileService.getPostsProfile(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), pageable));
                yield "layout/profile/myProfile/_videos";
            }
            case "reels" -> "layout/profile/myProfile/_reels";
            default -> {
                this.modelProfile(model, pageable);
                yield "layout/profile/myProfile/_posts";
            }
        };
    }

    @GetMapping("/{id}/loadSection")
    public String loadSectionView(@PathVariable("id") Long id, @RequestParam String section, Model model) {
        Pageable pageable = PageRequest.of(0, 7);
        return switch (section) {
            case "about" -> {
                this.modelProfileOther(model, id, pageable);
                yield "layout/profile/otherProfile/_about";
            }
            case "friends" -> "layout/profile/otherProfile/_friends";
            case "photos" -> {
                model.addAttribute("info", this.authenticationService.findAccountById(id));
                model.addAttribute("listPost", this.profileService.getPostsProfile(id, pageable));
                yield "layout/profile/otherProfile/_photos";
            }
            case "videos" -> {
                model.addAttribute("info", this.authenticationService.findAccountById(id));
                model.addAttribute("listPost", this.profileService.getPostsProfile(id, pageable));
                yield "layout/profile/otherProfile/_videos";
            }
            case "reels" -> "layout/profile/otherProfile/_reels";
            default -> {
                this.modelProfileOther(model, id, pageable);
                yield "layout/profile/otherProfile/_posts";
            }
        };
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        this.appService.deletePost(id);
        return "redirect:/profile";
    }

}
