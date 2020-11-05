package com.project.ecommerce.form;

import java.util.List;

public class CategoryForm {
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
