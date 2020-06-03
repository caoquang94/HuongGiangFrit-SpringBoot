package com.codegym.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "feedBacks")
@Where(clause = "deleted = 0")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String title;
    private String description;
    private short deleted;
    public FeedBack() {
    }

    public FeedBack(Long id, String name, String email, String title, String description, short deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }
}
