package com.project.ecommerce.form;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ProductForm implements Serializable {
    private static final long serialVersionUID = 5999069305725560297L;
    private String productName;
    private Integer categoryId;
    private Integer subCategoryId;
    private String description;
    private String brand;
    private String SKU;

    private String subjectAge;

    private String size;
    private String color;
    private String material;

    private String model;
    private String type;
    private String framePerSecond;
    private String weight;
    private String connectionPorts;

    private String capacity;
    private String power;
    private String voltage;

    private String author;
    private LocalDate releaseDate;
    private Integer numberOfPages;
    private String language;
    private String publisher;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFramePerSecond() {
        return framePerSecond;
    }

    public void setFramePerSecond(String framePerSecond) {
        this.framePerSecond = framePerSecond;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getConnectionPorts() {
        return connectionPorts;
    }

    public void setConnectionPorts(String connectionPorts) {
        this.connectionPorts = connectionPorts;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDelete2(String delete2) {
        this.delete2 = delete2;
    }

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
