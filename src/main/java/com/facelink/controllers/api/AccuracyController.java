package com.facelink.controllers.api;

import com.facelink.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accuracy")
public class AccuracyController {
    @Autowired
    private AuthenticationService service;


    @GetMapping("/isExists")
    ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(this.service.checkEmail(email));
    }

    @PostMapping("/response-password/{email}")
    ResponseEntity<?> responsePassword(@PathVariable("email") String email) {
        try {
            String password = this.service.generatorPassword();
            this.service.sendMail(email, password);
            this.service.updatePassword(email, password);
            return ResponseEntity.ok("Successfully updated password");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
