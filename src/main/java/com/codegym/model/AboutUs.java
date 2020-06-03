package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "aboutus")
@Where(clause = "deleted = 0")
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String htmlDescription;
    private String image;
    private short deleted;

    @Transient
    @JsonIgnore
    private CommonsMultipartFile[] fileImage;

    public CommonsMultipartFile[] getFileImage() {
        return fileImage;
    }

    public void setFileImage(CommonsMultipartFile[] fileImage) {
        this.fileImage = fileImage;
    }


    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AboutUs() {
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public AboutUs(Long id, String title, String description, String htmlDescription, String image, short deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.htmlDescription = htmlDescription;
        this.image = image;
        this.deleted = deleted;
    }
}
