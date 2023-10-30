package com.poc.blog.blogapis.dtos;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message = "Username must be minimum of 4 characters")
    private String name;
    @NotEmpty(message = "email address can't be empty")
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=4,max=20,message = "Password must be min 4 and max 20")
    private String password;
    @NotEmpty
    private String about;

    private Set<RoleDto> roles=new HashSet<>();

    @JsonIgnore
    public String getPassword(){
        return this.password;

    }
    @JsonProperty
    public void setPassword(String password){
        this.password=password;
    }
}
