package com.poc.blog.blogapis.repositories;

import com.poc.blog.blogapis.models.Category;
import com.poc.blog.blogapis.models.Post;
import com.poc.blog.blogapis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category user);
    @Query("select p from Post p where p.title like :key")
    List<Post> searchPostByTitle(@Param("key") String title);

}
