package com.poc.blog.blogapis.exceptions;

import java.io.Serial;

public class UserNotFoundException extends Exception{
    @Serial
    private static final long serialVersionUID = -5820842318241995567L;

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);

    }
}
