package com.poc.blog.blogapis.controllers;

import com.poc.blog.blogapis.dtos.JwtAuthRequest;
import com.poc.blog.blogapis.dtos.JwtAuthResponse;
import com.poc.blog.blogapis.dtos.UserDto;
import com.poc.blog.blogapis.exceptions.GenericException;
import com.poc.blog.blogapis.models.User;
import com.poc.blog.blogapis.security.JwtTokenHelper;
import com.poc.blog.blogapis.services.UserService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
        authenticate(request.getUserName(),request.getPassword());

        UserDetails userDetails=userDetailsService.loadUserByUsername((request.getUserName()));
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtTokenHelper.generateToken(userDetails));
        response.setUser(modelMapper.map((User)userDetails,UserDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.registerNewUser(userDto), HttpStatus.CREATED);
    }

    @SneakyThrows
    public void authenticate(String userName,String password){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userName,password);
       try {
           authenticationManager.authenticate(authToken);
       }
       catch (DisabledException e){
           throw new GenericException("User is disabled");
       }
       catch (BadCredentialsException e){
           throw new GenericException("Invalid Username or Password");
       }
    }
}
