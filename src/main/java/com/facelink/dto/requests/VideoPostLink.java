package com.facelink.dto.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VideoPostLink {
    private String content;
    private String type;
    private String link;
}
