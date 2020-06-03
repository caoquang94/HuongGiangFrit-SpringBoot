package com.codegym.service.Impl;

import com.codegym.model.Product;
import com.codegym.repository.ProductRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class ProductService implements com.codegym.service.ProductService<Product> {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product object) {
    productRepository.save(object);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        productRepository.deleteById(id);
    }


    public List<Product> findAllByCategories(long id) {
        return productRepository.findAllByCategories(id);
    }


}
