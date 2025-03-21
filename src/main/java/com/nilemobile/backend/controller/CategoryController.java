package com.nilemobile.backend.controller;

import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.service.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("/brand/get-all")
    public List<CategoryDTO> getAllBrands(){
        return categoryServiceImp.getAllBrands();
    }

    @GetMapping("/series/get-all/{brandName}")
    public List<CategoryDTO> getAllSeriesByBrand(@PathVariable String brandName){
        return categoryServiceImp.getAllSeries(brandName);
    }
}
