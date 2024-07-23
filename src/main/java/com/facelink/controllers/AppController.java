package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.entity.Post;
import com.facelink.service.AppService;
import com.facelink.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
public class AppController {
    @Autowired
    private AppService userService;
    @Autowired
    private AuthenticationService authenticationService;

    //Required
    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        try {
            return this.userService.getFriends(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        } catch (Exception e) {
            return null;
        }
    }

    @ModelAttribute("isLocked")
    public boolean isLocked() {
        return this.authenticationService.accountLocked(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    @GetMapping
    public String index(Model model, @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 7);
        model.addAttribute("post", new Post());
        model.addAttribute("listPost", this.userService.getPostsPublic(pageable));
        return "pages/home/index";
    }

    @PostMapping("/post")
    public String post(@Validated @ModelAttribute("post") Post post, BindingResult bindingResult, Model model) {
        if (post.getUrlVideo().isBlank() && post.getUrlImage().isBlank() && post.getContent().isBlank()) {
            bindingResult.rejectValue("content", "error", "Please try again!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            Pageable pageable = PageRequest.of(0, 7);
            model.addAttribute("listPost", this.userService.getPostsPublic(pageable));
            return "pages/home/index";
        }
        this.userService.postNew(post);
        return "redirect:/?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.userService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/view/{id}")
    public String edit(@PathVariable("id") Long id, Model model, @RequestParam("profile") int profile) {
        model.addAttribute("p", this.userService.findById(id));
        model.addAttribute("profile", profile);
        model.addAttribute("hasLike", this.userService.hasLike(id));
        model.addAttribute("reactionType", this.userService.getReactionType(id));
        return "pages/posts/view_post";
    }

    @PostMapping("/updatePost/{id}/{profile}")
    public String updatePostContent(@RequestParam("content") String content, @PathVariable("profile") int profile, @PathVariable("id") Long id) {
        this.userService.updatePostContent(content, id);
        return profile > 0 ? "redirect:/profile" : "redirect:/";
    }

    @GetMapping("/comment/{id}/{profile}")
    public String comment(@PathVariable("id") Long id, @PathVariable("profile") int profile, @RequestParam("content") String content, Model model) {
        if (!content.isBlank()) {
            this.userService.createComment(content, id);
        }
        return "redirect:/view/" + id + "?profile=" + profile;
    }

    @GetMapping("/delete-comment/{id}/{idComment}/{profile}")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("profile") int profile, @PathVariable("idComment") Long idComment) {
        this.userService.deleteComment(idComment);
        return "redirect:/view/" + id + "?profile=" + profile;
    }

    @GetMapping("/like/{id}/{profile}")
    public String like(@PathVariable("id") Long id, @PathVariable("profile") int profile, @RequestParam("type") String type) {
        this.userService.likeAndUnlike(id, type);
        return "redirect:/view/" + id + "?profile=" + profile;
    }

    @GetMapping("/demo")
    public String demo() {
        return "pages/other/demo";
    }
}
