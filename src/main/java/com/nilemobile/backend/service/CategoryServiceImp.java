package com.nilemobile.backend.service;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.request.CreateCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Categories createBrand(CreateCategoryRequest request) {

        return null;
    }

    @Override
    public List<CategoryDTO> getAllBrands() {
        List<Long> parentIds = categoryRepository.findParentIdsByName("Smartphone");
        return categoryRepository.findByLevel(2).stream()
                .filter(category -> parentIds.contains(category.getParentCategory().getCategories_id()))
                .map(category -> new CategoryDTO(
                        category.getCategories_id(),
                        category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllSeries(String brand){
        List<Long> parentIds = categoryRepository.findParentIdsByName(brand);
        return categoryRepository.findByLevel(3).stream()
                .filter(category -> parentIds.contains(category.getParentCategory().getCategories_id()))
                .map(category -> new CategoryDTO(
                        category.getCategories_id(),
                        category.getName()))
                .collect(Collectors.toList());
    }
}
