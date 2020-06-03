package com.codegym.service.Impl;

import com.codegym.model.Categories;
import com.codegym.repository.CategoriesRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class CategoriesService implements Service<Categories> {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public void save(Categories object) {
    categoriesRepository.save(object);
    }

    @Override
    public Categories findById(long id) {
        return categoriesRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
    categoriesRepository.deleteById(id);
    }
}
