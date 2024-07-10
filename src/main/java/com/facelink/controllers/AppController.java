package com.facelink.controllers;

import com.facelink.service.AuthenticationService;
import com.facelink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class AppController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @ModelAttribute("listFriends")
    public Set<?> listFriends() {
        return this.userService.getFriends();
    }

    @GetMapping
    public String index() {
        return "user/home";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/friends")
    public String friends() {
        return "user/coming-soon";
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
    public String demo(){
        return "user/demo";
    }
}
