package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    @Modifying
    @Query("update Product a set a.deleted=1 where a.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value="select p from Product p where p.id_c =:id")
    List<Product> findAllByCategories(@Param("id") long id);
}
