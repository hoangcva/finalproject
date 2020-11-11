package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.List;

public class CategoryForm implements Serializable {
    private static final long serialVersionUID = -2972869650381591062L;
    private Integer id;
    private String name;
    private List<SubCategoryForm> subCategoryForms;

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

    public List<SubCategoryForm> getSubCategoryForms() {
        return subCategoryForms;
    }

    public void setSubCategoryForms(List<SubCategoryForm> subCategoryForms) {
        this.subCategoryForms = subCategoryForms;
    }
}
