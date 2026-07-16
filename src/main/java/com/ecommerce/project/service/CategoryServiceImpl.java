package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.request.CategoryDTO;
import com.ecommerce.project.payload.request.CreateCategoryDTO;
import com.ecommerce.project.payload.response.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize, String sortBy, String sortOrder) {
        //implementing Sorting on the basis of ascending and descending format.
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        //implemented Pagination by giving parameters like page number and page size;
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty())
            throw new APIException("No Category created till now.");
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());


        return categoryResponse;
    }

    @Override
    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        Category existingCategory = categoryRepository.findByCategoryName(createCategoryDTO.getCategoryName());
        if(existingCategory != null)
            throw new APIException("Category with name" + createCategoryDTO.getCategoryName() + " Already exists!!");
        Category category = modelMapper.map(createCategoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class) ;

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

}