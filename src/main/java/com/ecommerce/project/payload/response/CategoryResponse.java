package com.ecommerce.project.payload.response;

import com.ecommerce.project.payload.request.CategoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<CategoryDTO> content;

}
