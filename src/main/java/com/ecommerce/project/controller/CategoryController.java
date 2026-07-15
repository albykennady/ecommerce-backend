package com.ecommerce.project.controller;

import com.ecommerce.project.payload.request.CategoryDTO;
import com.ecommerce.project.payload.request.CreateCategoryDTO;
import com.ecommerce.project.payload.response.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value="/public/categories",method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber") Integer pageNumber,
            @RequestParam(name="pageSize") Integer pageSize) {
        CategoryResponse categoryResponse = categoryService.getAllCategory(pageNumber,pageSize);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/public/categories",method = RequestMethod.POST)
    public  ResponseEntity<String> addCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO){
        categoryService.createCategory(createCategoryDTO);
        return new ResponseEntity<>("Category created successfully", HttpStatus.OK);
    }


    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            @PathVariable Long categoryId) {

        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

}
