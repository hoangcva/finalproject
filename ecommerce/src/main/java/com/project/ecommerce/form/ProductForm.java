package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.List;

public class ProductForm implements Serializable {
    private static final long serialVersionUID = 5999069305725560297L;
    private String productName;
    private Integer categoryId;
    private Integer subCategoryId;
    private String description;
    private String brand;
    private String SKU;
    private String size;
    private String color;
    private String subjectAge;
    private String material;
    private Long quantity;
    private Long price;
    private Integer rating;
    private Long vendorId;
    private Long productId;
    private Long createdBy;
    private String origin;
    private Long listPrice;
    private String vendorName;
    private String categoryName;
    private String subCategoryName;
    private List<ProductImageForm> productImageFormList;
//    private MultipartFile uploadFile1;
//    private MultipartFile uploadFile2;
//    private MultipartFile uploadFile3;
    private UploadImageForm uploadImage1;
    private UploadImageForm uploadImage2;
    private UploadImageForm uploadImage3;
    private String delete2;
    private boolean isSubmitted = false;

    public String getDelete2() {
        return delete2;
    }

    public void setDelete2(boolean delete2) {
        delete2 = delete2;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getListPrice() {
        return listPrice;
    }

    public void setListPrice(Long listPrice) {
        this.listPrice = listPrice;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public List<ProductImageForm> getProductImageFormList() {
        return productImageFormList;
    }

    public void setProductImageFormList(List<ProductImageForm> productImageFormList) {
        this.productImageFormList = productImageFormList;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

//    public MultipartFile getUploadFile1() {
//        return uploadFile1;
//    }
//
//    public void setUploadFile1(MultipartFile uploadFile1) {
//        this.uploadFile1 = uploadFile1;
//    }
//
//    public MultipartFile getUploadFile2() {
//        return uploadFile2;
//    }
//
//    public void setUploadFile2(MultipartFile uploadFile2) {
//        this.uploadFile2 = uploadFile2;
//    }
//
//    public MultipartFile getUploadFile3() {
//        return uploadFile3;
//    }
//
//    public void setUploadFile3(MultipartFile uploadFile3) {
//        this.uploadFile3 = uploadFile3;
//    }

    public UploadImageForm getUploadImage1() {
        return uploadImage1;
    }

    public void setUploadImage1(UploadImageForm uploadImage1) {
        this.uploadImage1 = uploadImage1;
    }

    public UploadImageForm getUploadImage2() {
        return uploadImage2;
    }

    public void setUploadImage2(UploadImageForm uploadImage2) {
        this.uploadImage2 = uploadImage2;
    }

    public UploadImageForm getUploadImage3() {
        return uploadImage3;
    }

    public void setUploadImage3(UploadImageForm uploadImage3) {
        this.uploadImage3 = uploadImage3;
    }
}
