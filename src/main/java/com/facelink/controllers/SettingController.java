package com.facelink.controllers;

import com.facelink.dto.AccountFullName;
import com.facelink.dto.AccountLoginChange;
import com.facelink.dto.CustomUser;
import com.facelink.entity.Account;
import com.facelink.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping
    public String setting(Model model) {
        Account account = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        model.addAttribute("info", AccountLoginChange.builder()
                .id(account.getId())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .password(account.getPassword()).build());
        return "pages/setting/index";
    }

    @MessageMapping("/user.update-name")
    @SendTo("/user/public")
    public void updateNameAccount(@Payload AccountFullName accountFullName) {
        this.settingService.updateInfo(accountFullName);
    }

    @PostMapping("/update-login")
    public String updateLogin(@Validated @ModelAttribute("info") AccountLoginChange accountLoginChange, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/setting/index";
        }
        this.settingService.updateLogin(accountLoginChange);
        return "redirect:/logout";
    }

    @GetMapping("/delete-account")
    public String deleteAccount(){
        this.settingService.deleteAccount(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
        return "redirect:/logout";
    }

}
