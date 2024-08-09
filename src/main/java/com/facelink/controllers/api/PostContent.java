package com.facelink.controllers.api;

import com.facelink.dto.requests.SendComment;
import com.facelink.dto.requests.VideoPostLink;
import com.facelink.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:8080", "http://facelink-webapp.ap-southeast-2.elasticbeanstalk.com/"})
public class PostContent {
    @Autowired
    private AppService appService;

    @PostMapping("/post-link")
    ResponseEntity<?> postLink(@RequestBody VideoPostLink post) {
        return ResponseEntity.ok(this.appService.postVideoLink(post));
    }

    @PostMapping("/send-comment")
    ResponseEntity<?> comment(@RequestBody SendComment sendComment) {
        return ResponseEntity.ok(this.appService.createComment(sendComment.getMessage(), sendComment.getPostId()));
    }

    @PostMapping("/like")
    ResponseEntity<?> likePost(@RequestParam("idPost") Long idPost, @RequestParam("type") String type) {
        return ResponseEntity.ok(this.appService.likeAndUnlike(idPost, type));
    }
}
