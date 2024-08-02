package com.facelink.controllers.api;

import com.facelink.dto.api.AccountResponse;
import com.facelink.dto.api.PostResponse;
import com.facelink.entity.Post;
import com.facelink.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ListPostController {
    @Autowired
    private AppService appService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        Page<Post> postsPage = (Page<Post>) this.appService.getPostsPublic(pageable);
        List<PostResponse> postResponses = postsPage.getContent()
                .stream()
                .map(this::convertToPostResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postResponses);
    }

    public PostResponse convertToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .type(post.getType().name())
                .content(post.getContent())
                .urlImage(post.getUrlImage())
                .urlVideo(post.getUrlVideo())
                .postAudience(post.getPostAudience().name())
                .account(AccountResponse.builder()
                        .id(post.getAccount().getId())
                        .email(post.getAccount().getEmail())
                        .fullName(post.getAccount().getAccountInfo().getFullName())
                        .build())
                .date(post.getCreateDate())
                .commentCount(post.getComments().size())
                .likesCount(post.getReactions().size())
                .build();
    }
}
