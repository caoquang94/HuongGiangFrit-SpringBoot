package com.codegym.service.Impl;

import com.codegym.model.Career;
import com.codegym.repository.CareerRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class CareerService implements Service<Career> {
    @Autowired
    private CareerRepository careerRepository;
    @Override
    public List<Career> findAll() {
        return careerRepository.findAll();
    }

    @Override
    public void save(Career object) {
        careerRepository.save(object);
    }

    @Override
    public Career findById(long id) {
        return careerRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        careerRepository.deleteById(id);
    }
}
