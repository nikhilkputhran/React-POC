package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.UserDto;
import com.poc.blog.blogapis.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
}
