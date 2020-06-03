package com.codegym.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> findAll();
    void save(T object);
    T findById(long id);
    void remove(long id);
}
