package com.poc.blog.blogapis.exceptions;

import java.io.Serial;

public class GenericException extends Exception{
    @Serial
    private static final long serialVersionUID = -5820842318241995567L;

    public GenericException(String errorMessage) {
        super(errorMessage);

    }
}
