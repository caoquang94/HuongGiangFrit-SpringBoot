package com.codegym.service.Impl;

import com.codegym.model.AboutUs;
import com.codegym.repository.AboutUsRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class AboutUsService implements Service<AboutUs> {
    @Autowired
    private AboutUsRepository aboutUsRepository;
    @Override
    public List<AboutUs> findAll() {
        return aboutUsRepository.findAll();
    }

    @Override
    public void save(AboutUs object) {
    aboutUsRepository.save(object);
    }

    @Override
    public AboutUs findById(long id) {
        return aboutUsRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
    aboutUsRepository.deleteById(id);
    }
}
