package com.codewithratchez.blog.controllers;

import com.codewithratchez.blog.exceptions.ApiException;
import com.codewithratchez.blog.payloads.JwtAuthRequest;
import com.codewithratchez.blog.payloads.UserDto;
import com.codewithratchez.blog.payloads.UserRegReqDto;
import com.codewithratchez.blog.payloads.UserRegRespDto;
import com.codewithratchez.blog.security.JwtAuthResponse;
import com.codewithratchez.blog.security.JwtTokenHelper;
import com.codewithratchez.blog.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth/")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            System.out.println("Invalid Details");
            throw new ApiException("Invalid username or password");
        }
    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserRegRespDto> registerUser(@Valid @RequestBody UserRegReqDto userDto){
        System.out.println(userDto);
        UserRegRespDto registeredUser = userService.registerNewUser(userDto);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
