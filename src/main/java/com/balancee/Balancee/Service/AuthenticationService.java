package com.balancee.Balancee.Service;

import com.balancee.Balancee.Domain.Dto.CustomerDTO;
import com.balancee.Balancee.Domain.Dto.LoginDto;
import com.balancee.Balancee.Response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> storeCustomer(CustomerDTO customerDTO);

    LoginResponse login(LoginDto loginDto);

    //void createAdminUsers();
}
