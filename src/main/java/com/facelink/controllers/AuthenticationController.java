package com.facelink.controllers;

import com.facelink.entity.Account;
import com.facelink.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @ModelAttribute("account")
    public Account accountForm() {
        return new Account();
    }
    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "authentication/signup";
    }
    @PostMapping("/signup")
    public String signup(@ModelAttribute("account") Account account) {
        System.out.println(this.authenticationService.save(account));
        return "redirect:/login";
    }
}
