package com.poc.blog.blogapis.dtos;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String userName;
    private String password;
}
