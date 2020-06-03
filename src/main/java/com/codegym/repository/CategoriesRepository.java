package com.codegym.repository;

import com.codegym.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Override
    @Modifying
    @Query("update Categories a set a.deleted=1 where a.id=:id")
    void deleteById(@Param("id") Long id);
}
