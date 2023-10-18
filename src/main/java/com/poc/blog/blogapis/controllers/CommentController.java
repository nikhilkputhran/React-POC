package com.poc.blog.blogapis.controllers;

import com.poc.blog.blogapis.dtos.CommentDto;
import com.poc.blog.blogapis.dtos.PostDto;
import com.poc.blog.blogapis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createPost(@RequestBody @Valid CommentDto commentDto,
                                              @PathVariable Integer postId){
        return new ResponseEntity<>(commentService.createComment(commentDto,postId), HttpStatus.CREATED);
    }
}
