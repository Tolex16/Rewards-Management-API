package com.balancee.Balancee.Service.ServiceImpl;


import com.balancee.Balancee.Domain.Dto.CustomerDTO;
import com.balancee.Balancee.Domain.Dto.LoginDto;
import com.balancee.Balancee.Domain.Entity.Customer;
import com.balancee.Balancee.Domain.Entity.Role;
import com.balancee.Balancee.ExpectionHandling.CustomerAlreadyExistsException;
import com.balancee.Balancee.Repository.CustomerRepository;
import com.balancee.Balancee.Response.LoginResponse;
import com.balancee.Balancee.Service.AuthenticationService;
import com.balancee.Balancee.Service.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;

    @Autowired
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> storeCustomer(CustomerDTO customerDTO){
        if (customerRepository.existsByEmail(customerDTO.getEmail())){
            throw new CustomerAlreadyExistsException("There is a customer account associated with this email already");
        }

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setMiddleName(customerDTO.getMiddleName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setRole(Role.CUSTOMER);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

        customerRepository.save(customer);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public LoginResponse login(LoginDto loginDto)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (BadCredentialsException e){
            throw new IllegalArgumentException("Invalid email and Password", e);
        }

        var customer = customerRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("Error in email and password"));
        var jwt = jwtService.genToken(customer);


        return new LoginResponse(jwt);
    }

  //  @PostConstruct
//    public void createAdminUsers() {
//        Optional<Customer> adminUser = customerRepository.findByEmail("techbalanee@gmail.com");
//        if (!adminUser.isPresent()) {
//            Customer createAdmin = new Customer();
//            createAdmin.setFirstName("Michael");
//            createAdmin.setMiddleName("Chima");
//            createAdmin.setLastName("Anozie");
//            createAdmin.setEmail("techbalancee@gmail.com");
//            createAdmin.setPassword(passwordEncoder.encode("Tolex@011"));
//            createAdmin.setRole(Role.ADMIN);
//            customerRepository.save(createAdmin);
//        }



}
