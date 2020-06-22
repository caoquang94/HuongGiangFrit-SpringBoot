package com.codegym.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Where(clause = "deleted = 0")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private short deleted;

    public Account() {
    }

    public Account(Long id, String username, String password, short deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }
}
