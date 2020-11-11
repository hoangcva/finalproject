package com.project.ecommerce.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
    private static final long serialVersionUID = -6108378333398627517L;
    private Integer id;
    private String productName;
    private String description;
    private Integer categoryId;
    private Integer subCategoryId;
    private String brand;
    private String SKU;
    private String origin;
    private Long createdBy;
    private Long listPrice;

    public ProductDto() {
    }

    public ProductDto(Integer id, String productName, String description, Integer categoryId, Integer subCategoryId, String brand, String SKU, String origin, Long createdBy, Long listPrice) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.brand = brand;
        this.SKU = SKU;
        this.origin = origin;
        this.createdBy = createdBy;
        this.listPrice = listPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }


    public Long getListPrice() {
        return listPrice;
    }

    public void setListPrice(Long listPrice) {
        this.listPrice = listPrice;
    }
}
