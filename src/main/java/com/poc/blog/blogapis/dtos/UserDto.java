package com.poc.blog.blogapis.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message = "Username must be minimum of 4 characters")
    private String name;
    @NotEmpty
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=4,max=20,message = "Password must be min 4 and max 20")
    private String password;
    @NotEmpty
    private String about;
}
