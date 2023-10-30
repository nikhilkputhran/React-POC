package com.poc.blog.blogapis.security;

import com.poc.blog.blogapis.exceptions.ResourceNotFoundException;
import com.poc.blog.blogapis.models.User;
import com.poc.blog.blogapis.repositories.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.poc.blog.blogapis.constants.constantUtils.USER_NOT_FOUND;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    @SneakyThrows
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException(USER_NOT_FOUND));

    }

}
