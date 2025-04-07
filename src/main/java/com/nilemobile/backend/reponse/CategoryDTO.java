package com.nilemobile.backend.reponse;

import com.nilemobile.backend.model.Categories;

public class CategoryDTO {
    private Long categoryId;
    private String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryDTO(Categories categories) {
        this.categoryId = categories.getCategories_id();
        this.categoryName = categories.getName();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
