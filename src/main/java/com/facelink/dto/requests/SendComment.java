package com.facelink.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendComment {
    private String message;
    private Long postId;
}
