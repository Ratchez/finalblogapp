package com.codewithratchez.blog.services.impl;

import com.codewithratchez.blog.entities.Category;
import com.codewithratchez.blog.exceptions.ResourceNotFoundException;
import com.codewithratchez.blog.payloads.CategoryDto;
import com.codewithratchez.blog.repositories.CategoryRepo;
import com.codewithratchez.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory = categoryRepo.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId){
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category"));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }
    @Override
    public CategoryDto getCategory(Integer categoryId){
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category"));
        return this.modelMapper.map(category, CategoryDto.class);
    }
    @Override
    public List<CategoryDto> getCategories(){
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
    @Override
    public void deleteCategory(Integer categoryId){
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category"));
        categoryRepo.delete(category);
    }
}
