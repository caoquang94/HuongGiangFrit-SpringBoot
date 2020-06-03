package com.codegym.repository;

import com.codegym.model.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    @Override
    @Modifying
    @Query("update AboutUs a set a.deleted=1 where a.id=:id")
    void deleteById(@Param("id") Long id);
}
