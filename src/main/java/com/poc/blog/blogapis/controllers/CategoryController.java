package com.poc.blog.blogapis.controllers;

import com.poc.blog.blogapis.dtos.CategoryDto;
import com.poc.blog.blogapis.dtos.CategoryDto;
import com.poc.blog.blogapis.services.CategoryService;
import com.poc.blog.blogapis.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/blog/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto,@PathVariable("categoryId") Integer categoryId)
    {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId)
    {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
}
