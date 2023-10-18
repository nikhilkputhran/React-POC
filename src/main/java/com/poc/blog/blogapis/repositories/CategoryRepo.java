package com.poc.blog.blogapis.repositories;

import com.poc.blog.blogapis.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
