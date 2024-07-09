package com.facelink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping
    public String index() {
        return "home/home";
    }
    @GetMapping("/friends")
    public String friends() {
        return "home/coming-soon";
    }
    @GetMapping("/videos")
    public String videos() {
        return "home/coming-soon";
    }
    @GetMapping("/marketplace")
    public String marketplace() {
        return "home/coming-soon";
    }
    @GetMapping("/groups")
    public String groups() {
        return "home/coming-soon";
    }
}
