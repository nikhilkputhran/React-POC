package com.poc.blog.blogapis.services;

import com.poc.blog.blogapis.dtos.CategoryDto;
import com.poc.blog.blogapis.exceptions.ResourceNotFoundException;
import com.poc.blog.blogapis.models.Category;
import com.poc.blog.blogapis.repositories.CategoryRepo;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.poc.blog.blogapis.constants.constantUtils.CATEGORY_NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=categoryRepo.save(dtoToCategory(categoryDto));
         return categoryToDto(category);
    }
    @SneakyThrows
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory=categoryRepo.save(category);
        return categoryToDto(updatedCategory);
    }

    @SneakyThrows
    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> user=categoryRepo.findAll();
        return user.stream().map(this::categoryToDto).collect(Collectors.toList());
    }


    private Category dtoToCategory(CategoryDto categoryDto)
    {
        return modelMapper.map(categoryDto, Category.class);
    }
    private CategoryDto categoryToDto(Category category)
    {
        return modelMapper.map(category, CategoryDto.class);
    }
}
