package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.reponse.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(String name);

    @Query("SELECT c FROM Categories c WHERE c.level = :level")
    List<Categories> findByLevel(int level);

    @Query("SELECT c.categories_id FROM Categories c WHERE c.name = :name")
    List<Long> findParentIdsByName(String name);

    @Query("SELECT c FROM Categories c WHERE c.name = :name AND c.parentCategory = :parent")
    Optional<Categories> findByNameAndParentCategory(@Param("name") String name, @Param("parent") Categories parentCategory);
}
