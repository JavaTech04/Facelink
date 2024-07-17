package com.facelink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    @GetMapping
    public String marketplace() {
        return "pages/other/coming-soon";
    }
}
