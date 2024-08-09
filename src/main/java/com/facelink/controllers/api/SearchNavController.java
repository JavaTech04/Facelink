package com.facelink.controllers.api;

import com.facelink.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchNavController {
    @Autowired
    private AppService appService;

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestParam String query) {
        return ResponseEntity.ok(this.appService.search(query.trim()));
    }
}
