package com.codegym.service;

import java.util.List;

public interface ProductService<T> {
    List<T> findAll();
    void save(T object);
    T findById(long id);
    void remove(long id);
//    List<T> findAllByCategories();
}
