package com.project.ecommerce.dto;

public class ProductDto {
    private Integer id;
    private String productName;
    private String description;
    private Integer categoryId;
    private Integer subCategoryId;
    private String brand;
    private String SKU;
    private String size;
    private String color;
    private String subjectAge;
    private String material;

    public ProductDto() {
    }

    public ProductDto(Integer id, String productName, String description, Integer categoryId, Integer subCategoryId,
                      String brand, String SKU, String size, String color, String subjectAge, String material) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.brand = brand;
        this.SKU = SKU;
        this.size = size;
        this.color = color;
        this.subjectAge = subjectAge;
        this.material = material;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubjectAge() {
        return subjectAge;
    }

    public void setSubjectAge(String subjectAge) {
        this.subjectAge = subjectAge;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
