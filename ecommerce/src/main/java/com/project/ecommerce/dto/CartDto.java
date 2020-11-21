package com.project.ecommerce.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CartDto implements Serializable {
    private static final long serialVersionUID = -4293241350577157144L;
    private long id;
    private long customerId;
    private long productId;
    private long vendorId;
    private long buyQuantity;
    private LocalDateTime insertTime;

    public CartDto() {
    }

    public CartDto(long customerId, long productId, long vendorId, long buyQuantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.vendorId = vendorId;
        this.buyQuantity = buyQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public long getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(long buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }
}
