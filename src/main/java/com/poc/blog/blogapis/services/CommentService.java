package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.CategoryDto;
import com.poc.blog.blogapis.dtos.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);
    CommentDto updateComment(CommentDto commentDto,Integer commentId);
    CommentDto getCommentById(Integer commentId);
    List<CommentDto> getAllComments();
    void deleteComment(Integer commentId);
}
