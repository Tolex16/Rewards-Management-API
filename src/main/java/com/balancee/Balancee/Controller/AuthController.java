package com.balancee.Balancee.Controller;

import com.balancee.Balancee.Domain.Dto.CustomerDTO;
import com.balancee.Balancee.Domain.Dto.LoginDto;
import com.balancee.Balancee.Response.LoginResponse;
import com.balancee.Balancee.Service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> storeUser(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result){
        System.out.println("Has errors?" + result.hasErrors());
        if (result.hasErrors()){ return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}

        return authenticationService.storeCustomer(customerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity <LoginResponse> login(@Valid @RequestBody LoginDto logindto, BindingResult result){
        System.out.println("Has errors?" + result.hasErrors());
        if (result.hasErrors()){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}

        return ResponseEntity.ok(authenticationService.login(logindto));
    }
}
