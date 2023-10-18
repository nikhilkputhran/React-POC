package com.poc.blog.blogapis.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer categoryId;

    @NotEmpty
    @Size(min = 4,message = "min chars for title must be 4")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10,message = "min chars for description must be 10")
    private String categoryDescription;
}
