package com.project.ecommerce.dto;

import java.io.Serializable;

public class VendorProductDto implements Serializable {
    private static final long serialVersionUID = -3207194978722285608L;
    private Long vendorId;
    private Long productId;
    private Long quantity;
    private Long price;
    private Integer rating;
    private String size;
    private String color;
    private String subjectAge;
    private String material;

    public VendorProductDto() {
    }

    public VendorProductDto(Long vendorId, Long productId, Long quantity, Long price, Integer rating, String size, String color, String subjectAge, String material) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.rating = rating;
        this.size = size;
        this.color = color;
        this.subjectAge = subjectAge;
        this.material = material;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
