package com.codegym.repository;

import com.codegym.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsRepository extends JpaRepository<News, Long> {
    @Override
    @Modifying
    @Query("update News a set a.deleted=1 where a.id=:id")
    void deleteById(@Param("id") Long id);

    @Query("select e from News e order by e.id DESC")
    List<News> findAll();
}
