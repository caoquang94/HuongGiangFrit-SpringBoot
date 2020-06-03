package com.codegym.repository;

import com.codegym.model.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    @Override
    @Modifying
    @Query("update FeedBack a set a.deleted=1 where a.id=:id")
    void deleteById(@Param("id") Long id);
}
