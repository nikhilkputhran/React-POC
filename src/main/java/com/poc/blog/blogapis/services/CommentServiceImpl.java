package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.CommentDto;
import com.poc.blog.blogapis.exceptions.ResourceNotFoundException;
import com.poc.blog.blogapis.models.Comment;
import com.poc.blog.blogapis.models.Post;
import com.poc.blog.blogapis.repositories.CommentRepo;
import com.poc.blog.blogapis.repositories.PostRepo;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.poc.blog.blogapis.constants.constantUtils.COMMENT_NOT_FOUND;
import static com.poc.blog.blogapis.constants.constantUtils.POST_NOT_FOUND;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    PostRepo postRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @SneakyThrows
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post=postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException(POST_NOT_FOUND));
        Comment comment=dtoToComment(commentDto);
        comment.setPost(post);
        return commentToDto(commentRepo.save(comment));
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        return null;
    }

    @Override
    public CommentDto getCommentById(Integer commentId) {
        return null;
    }

    @Override
    public List<CommentDto> getAllComments() {
        return null;
    }

    @Override
    @SneakyThrows
    public void deleteComment(Integer commentId) {
        Comment comment=commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException(COMMENT_NOT_FOUND));
        commentRepo.delete(comment);

    }

    private Comment dtoToComment(CommentDto commentDto)
    {
        return modelMapper.map(commentDto, Comment.class);
    }
    private CommentDto commentToDto(Comment comment)
    {
        return modelMapper.map(comment,CommentDto.class);
    }
}
