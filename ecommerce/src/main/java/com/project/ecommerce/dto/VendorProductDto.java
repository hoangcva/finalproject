package com.project.ecommerce.dto;

public class VendorProductDto {
    private Long vendorId;
    private Integer productId;
    private Long quantity;
    private Long price;
    private Integer rating;

    public VendorProductDto() {
    }

    public VendorProductDto(Long vendorId, Integer productId, Long quantity, Long price, Integer rating) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.rating = rating;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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
}
