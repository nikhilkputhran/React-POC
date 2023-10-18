package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.PostDto;
import com.poc.blog.blogapis.dtos.PostResponse;
import com.poc.blog.blogapis.exceptions.ResourceNotFoundException;
import com.poc.blog.blogapis.models.Category;
import com.poc.blog.blogapis.models.Post;
import com.poc.blog.blogapis.models.User;
import com.poc.blog.blogapis.repositories.CategoryRepo;
import com.poc.blog.blogapis.repositories.PostRepo;
import com.poc.blog.blogapis.repositories.UserRepo;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.poc.blog.blogapis.constants.constantUtils.*;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;



    @Override
    @SneakyThrows
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {

        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException(USER_NOT_FOUND));
        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        Post post=dtoToPost(postDto);
        post.setImageName("nikhil.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        return postToDto(postRepo.save(post));
    }

    @Override
    @SneakyThrows
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post=postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException(POST_NOT_FOUND));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        return postToDto(postRepo.save(post));
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> ps=postRepo.findAll(p);
        List<Post> posts=ps.getContent();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(posts.stream().map(this::postToDto).toList());
        postResponse.setPageNumber(ps.getNumber());
        postResponse.setPageSize(ps.getSize());
        postResponse.setTotalElements(ps.getTotalElements());
        postResponse.setTotalPages(ps.getTotalPages());
        postResponse.setLastPage(ps.isLast());
        return postResponse;

    }

    @Override
    @SneakyThrows
    public PostDto getPostById(Integer postId) {
        Post post=postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException(POST_NOT_FOUND));
        return postToDto(post);
    }

    @Override
    @SneakyThrows
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        List<Post> posts=postRepo.findByCategory(category);
         return posts.stream().map(this::postToDto).collect(Collectors.toList());

    }

    @Override
    @SneakyThrows
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException(USER_NOT_FOUND));
        List<Post> posts=postRepo.findByUser(user);
        return posts.stream().map(this::postToDto).toList();

    }

    @Override
    public List<PostDto> getPostsByTitleContaining(String title) {
        List<Post> posts=postRepo.searchPostByTitle("%"+title+"%");
       return posts.stream().map(this::postToDto).toList();
    }

    private Post dtoToPost(PostDto postDto)
    {
        return modelMapper.map(postDto, Post.class);
    }
    private PostDto postToDto(Post post)
    {
        return modelMapper.map(post, PostDto.class);
    }
}
