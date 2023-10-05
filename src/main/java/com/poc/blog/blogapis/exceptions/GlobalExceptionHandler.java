package com.poc.blog.blogapis.exceptions;


import com.poc.blog.blogapis.dtos.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GenericResponse> userNotFountExceptionHandler(UserNotFoundException ex){
        GenericResponse genericResponse=new GenericResponse(ex.getMessage(),false);
        return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
    }
}
