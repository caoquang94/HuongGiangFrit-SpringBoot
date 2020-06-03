package com.codegym.service.Impl;

import com.codegym.model.News;
import com.codegym.repository.NewsRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class NewService implements Service<News> {
    @Autowired
    private NewsRepository newsRepository;
    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public void save(News object) {
    newsRepository.save(object);
    }

    @Override
    public News findById(long id) {
        return newsRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        newsRepository.deleteById(id);
    }
}
