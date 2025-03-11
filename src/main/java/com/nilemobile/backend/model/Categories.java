package com.nilemobile.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categories_id;

    @NotNull
    @Size(max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private Categories parentCategory;

    private int level;

    public Categories() {

    }

    public Categories(Long categories_id, String name, Categories parentCategory, int level) {
        this.categories_id = categories_id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.level = level;
    }

    public Long getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(Long categories_id) {
        this.categories_id = categories_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Categories parentCategory) {
        this.parentCategory = parentCategory;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
