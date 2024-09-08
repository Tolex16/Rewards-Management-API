package com.balancee.Balancee.Response;

import com.balancee.Balancee.Domain.Dto.CustomerDTO;
import com.balancee.Balancee.Domain.Entity.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty("token")
    private final String token;
}
