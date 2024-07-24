package com.facelink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountLoginChange {
    private Long id;
    @NotBlank(message = "Do not leave emails blank!")
    @Email(message = "Email invalidate!")
    private String email;
    private String phoneNumber;
    private String password;
}
