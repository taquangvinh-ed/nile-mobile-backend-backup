package com.nilemobile.backend.service;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    public Categories createBrand(CreateCategoryRequest request);
    public Categories updateBrand(Long id, CategoryDTO categoryDTO);
    public void deleteBrandById(Long id);
    public List<CategoryDTO> getAllBrands();
    public Categories createSeries(Long brandId, CreateCategoryRequest request);
    public Categories updateSeries(Long id, CategoryDTO categoryDTO);
    public void deleteSeriesById(Long id);
    public List<CategoryDTO> getAllSeries(Long brandId);
}
