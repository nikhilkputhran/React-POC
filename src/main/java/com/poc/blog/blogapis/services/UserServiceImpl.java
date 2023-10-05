package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.UserDto;
import com.poc.blog.blogapis.exceptions.UserNotFoundException;
import com.poc.blog.blogapis.models.User;
import com.poc.blog.blogapis.repositories.UserRepo;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.poc.blog.blogapis.constants.constantUtils.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
         User user=userRepo.save(dtoToUser(userDto));
         return userToDto(user);
    }
    @SneakyThrows
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepo.findById(userId)
                .orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=userRepo.save(user);
        return userToDto(updatedUser);
    }

    @SneakyThrows
    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepo.findById(userId)
                .orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> user=userRepo.findAll();
        return user.stream().map(this::userToDto).collect(Collectors.toList());
    }


    private User dtoToUser(UserDto userDto)
    {
        return modelMapper.map(userDto, User.class);
    }
    private UserDto userToDto(User user)
    {
        return modelMapper.map(user, UserDto.class);
    }
}
