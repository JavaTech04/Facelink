package com.facelink.controllers;

import com.facelink.dto.CustomUser;
import com.facelink.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private AuthenticationService authenticationService;

    @ModelAttribute("isLocked")
    public boolean isLocked() {
        return this.authenticationService.accountLocked(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    @GetMapping
    public String groups() {
        return "pages/other/coming-soon";
    }
}
