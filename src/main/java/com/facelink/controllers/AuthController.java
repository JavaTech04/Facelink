package com.facelink.controllers;

import com.facelink.entity.Account;
import com.facelink.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @ModelAttribute("account")
    public Account accountForm() {
        return new Account();
    }
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }
    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("account") Account account, BindingResult bindingResult, Model model) {
        if(account.getPassword().length() < 6){
            bindingResult.rejectValue("password", "Password should be at least 6 characters");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            return "auth/signup";
        }
        this.authenticationService.save(account);
        return "redirect:/login";
    }

}
