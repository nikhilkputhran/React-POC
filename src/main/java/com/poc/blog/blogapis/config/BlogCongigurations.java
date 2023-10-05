package com.poc.blog.blogapis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class BlogCongigurations {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
