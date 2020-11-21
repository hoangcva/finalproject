package com.project.ecommerce.form;

import java.io.Serializable;

public class ViewProductDetailForm implements Serializable {
    private static final long serialVersionUID = -8879638965603575349L;
    private Long quantity;
    private Long vendorId;
    private Long productId;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
}
