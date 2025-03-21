package com.nilemobile.backend.service;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    public Categories createBrand(CreateCategoryRequest request);
    public List<CategoryDTO> getAllBrands();
    public List<CategoryDTO> getAllSeries(String brand);
}
