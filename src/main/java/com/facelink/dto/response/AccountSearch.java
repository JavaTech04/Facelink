package com.facelink.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountSearch {
    private Long id;
    private String avatar;
    private String fullName;
}
