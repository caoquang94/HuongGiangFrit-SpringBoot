package com.codegym.service.Impl;

import com.codegym.model.Contact;
import com.codegym.repository.ContactRepository;
import com.codegym.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class ContactService implements Service<Contact> {
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public void save(Contact object) {
        contactRepository.save(object);
    }

    @Override
    public Contact findById(long id) {
        return contactRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        contactRepository.deleteById(id);
    }
}
