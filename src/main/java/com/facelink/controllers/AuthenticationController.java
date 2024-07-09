package com.facelink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "authentication/signup";
    }
}
