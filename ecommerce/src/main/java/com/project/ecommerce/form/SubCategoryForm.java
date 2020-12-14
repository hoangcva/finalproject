package com.project.ecommerce.form;

import java.io.Serializable;

public class SubCategoryForm implements Serializable {
    private static final long serialVersionUID = 3755138838093653405L;
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private boolean isSubmitted;

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
