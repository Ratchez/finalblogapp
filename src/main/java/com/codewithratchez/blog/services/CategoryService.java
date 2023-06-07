package com.codewithratchez.blog.services;

import com.codewithratchez.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    //delete
    void deleteCategory(Integer categoryId);
    //get all users
    List<CategoryDto> getCategories();
    //get specific user
    CategoryDto getCategory(Integer categoryId);
}
