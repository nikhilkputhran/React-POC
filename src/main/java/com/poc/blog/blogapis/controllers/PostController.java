package com.poc.blog.blogapis.controllers;


import com.poc.blog.blogapis.dtos.PostDto;
import com.poc.blog.blogapis.dtos.PostResponse;
import com.poc.blog.blogapis.dtos.UserDto;
import com.poc.blog.blogapis.services.FileService;
import com.poc.blog.blogapis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PostController
{
    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;
    
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable("postId") Integer postId)
    {
        return ResponseEntity.ok(postService.updatePost(postDto,postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = "postId",required = false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        return ResponseEntity.ok(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId)
    {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser( @PathVariable Integer userId){
        return ResponseEntity.ok(postService.getPostsByUser(userId));

    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory( @PathVariable Integer categoryId){
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));

    }

    @GetMapping("/post/search/{title}")
    public ResponseEntity<List<PostDto>> getsearchByTitle( @PathVariable String title){
        return ResponseEntity.ok(postService.getPostsByTitleContaining(title));

    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@PathVariable Integer postId,
                                            @RequestParam("image")MultipartFile image) throws IOException {
        PostDto postDto=postService.getPostById(postId);
        String fileName=fileService.uploadFile(path,image);
        postDto.setImageName(fileName);
        return ResponseEntity.ok(postService.updatePost(postDto,postId));

    }

    @GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadInage(
            @PathVariable String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }


}
