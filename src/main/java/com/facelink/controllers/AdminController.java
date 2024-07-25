package com.facelink.controllers;

import com.facelink.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("data", this.adminService.getAllAccount());
        return "pages/admin/index";
    }


    @GetMapping("/lockAccount/{id}")
    public String lockAccount(@PathVariable("id") Long id, @RequestParam("admin-page") Optional<Boolean> adminPage) {
        this.adminService.lockAccount(id);
        if (adminPage.isPresent()) {
            return "redirect:/admin";
        }
        return "redirect:/profile/" + id;
    }

    @GetMapping("/unlockAccount/{id}")
    public String unlockAccount(@PathVariable("id") Long id, @RequestParam("admin-page") Optional<Boolean> adminPage) {
        this.adminService.unlockAccount(id);
        if (adminPage.isPresent()) {
            return "redirect:/admin";
        }
        return "redirect:/profile/" + id;
    }

    @GetMapping("/verifiedAccount/{id}")
    public String verifiedAccount(@PathVariable("id") Long id) {
        this.adminService.verifiedAccount(id);
        return "redirect:/admin";
    }

    @GetMapping("/unVerifiedAccount/{id}")
    public String unVerifiedAccount(@PathVariable("id") Long id) {
        this.adminService.unVerifiedAccount(id);
        return "redirect:/admin";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        this.adminService.deleteAccount(id);
        return "redirect:/admin";
    }

    @GetMapping("/setAdmin/{id}")
    public String setAdmin(@PathVariable("id") Long id) {
        this.adminService.setAdmin(id);
        return "redirect:/admin";
    }

    @GetMapping("/removeAdmin/{id}")
    public String removeAdmin(@PathVariable("id") Long id) {
        this.adminService.removeAdmin(id);
        return "redirect:/admin";
    }
}
