package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Where(clause = "deleted=0")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private short deleted;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private  Set<Product> products;

    public Set<Product> getProducts() {
        return products;
    }

    public short getIsDeleted() {
        return deleted;
    }

    public void setIsDeleted(short deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories() {
    }

    public Categories(long id,String description, String name, short deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.description = description;
    }

}
