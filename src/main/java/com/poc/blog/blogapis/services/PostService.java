package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.PostDto;
import com.poc.blog.blogapis.dtos.PostResponse;
import com.poc.blog.blogapis.models.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postdto,Integer postId);

    PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> getPostsByTitleContaining(String title);














}
