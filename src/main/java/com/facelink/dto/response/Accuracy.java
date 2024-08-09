package com.facelink.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Accuracy {
    public String email;
    private Boolean isLocked;
    private Boolean isEnabled;
}
