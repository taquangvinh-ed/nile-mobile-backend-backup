package com.nilemobile.backend.request;

public class CreateCategoryRequest {
    private Long categoryId;
    private String categoryName;
    private Long categoryParentId;

    public CreateCategoryRequest(Long categoryId, String categoryName, Long categoryParentId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryParentId = categoryParentId;
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

    public Long getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Long categoryParentId) {
        this.categoryParentId = categoryParentId;
    }
}
