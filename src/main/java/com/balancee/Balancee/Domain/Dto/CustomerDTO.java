package com.balancee.Balancee.Domain.Dto;


import com.balancee.Balancee.Config.StrongPassword;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class CustomerDTO {

    private String firstName;

    private String middleName;

    private String lastName;

    @Email
    private String email;

    @StrongPassword
    private String password;
}
