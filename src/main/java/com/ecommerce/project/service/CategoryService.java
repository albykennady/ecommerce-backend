package com.ecommerce.project.service;

import com.ecommerce.project.payload.request.CategoryDTO;
import com.ecommerce.project.payload.request.CreateCategoryDTO;
import com.ecommerce.project.payload.response.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {
    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize);
    void createCategory(CreateCategoryDTO createCategoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(@Valid CategoryDTO categoryDTO, Long categoryId);
}
