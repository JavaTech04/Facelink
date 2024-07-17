package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.entity.Post;
import com.facelink.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
public class AppController {
    @Autowired
    private AppService userService;

    //Required
    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.userService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        } catch (Exception _) {
            return null;
        }
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("listPost", this.userService.getPostsPublic());
        return "pages/home/index";
    }

    @PostMapping("/post")
    public String post(@Validated @ModelAttribute("post") Post post, BindingResult bindingResult, Model model) {
        if(post.getUrlVideo().isBlank() && post.getUrlImage().isBlank() && post.getContent().isBlank()) {
            bindingResult.rejectValue("content", "error", "Please try again!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("post",post);
            model.addAttribute("listPost", this.userService.getPostsPublic());
            return "pages/home/index";
        }
        this.userService.postNew(post);
        return "redirect:/?success";
    }

    @GetMapping("/demo")
    public String demo() {
        return "pages/other/demo";
    }
}
