package com.codegym.model;

import com.codegym.controller.admin.web.AdminBaseController;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Where(clause = "deleted = 0")
public class Contact extends AdminBaseController {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String phone;
    private String email;
    private short deleted;

    public Contact() {
    }

    public Contact(Long id, String address, String phone, String email, short deleted) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }
}
