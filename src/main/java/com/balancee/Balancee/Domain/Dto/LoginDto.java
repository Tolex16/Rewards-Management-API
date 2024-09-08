package com.balancee.Balancee.Domain.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @Email(message = "Input a valid email address")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
