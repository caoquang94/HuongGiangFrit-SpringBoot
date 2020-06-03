package com.codegym.service.Impl;

import com.codegym.model.FeedBack;
import com.codegym.repository.FeedBackRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class FeedBackService implements Service<FeedBack> {
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Override
    public List<FeedBack> findAll() {
        return feedBackRepository.findAll();
    }

    @Override
    public void save(FeedBack object) {
        feedBackRepository.save(object);
    }

    @Override
    public FeedBack findById(long id) {
        return feedBackRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        feedBackRepository.deleteById(id);
    }
}
