package com.facelink.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResponse {
    private Long id;
    private String type;
    private String content;
    private String urlImage;
    private String urlVideo;
    private String postAudience;

    private AccountResponse account;

    private LocalDateTime date;

    private int commentCount;
    private int likesCount;

}
