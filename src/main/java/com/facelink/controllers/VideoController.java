package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.service.AuthenticationService;
import com.facelink.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AuthenticationService authenticationService;

    @ModelAttribute("isLocked")
    public boolean isLocked() {
        return this.authenticationService.accountLocked(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    @GetMapping
    public String videos(Model model) {
        model.addAttribute("videos", this.videoService.getAllVideos());
        return "pages/videos/index";
    }
}
