package com.project.ecommerce.form;

import java.io.Serializable;

public class OrderDetailForm implements Serializable {
    private static final long serialVersionUID = 306361087336855267L;
    private long orderId;
    private long productId;
    private long vendorId;
    private long buyQuantity;
    private long price;
    private byte[] image;
    private ProductForm productForm;
    private VendorForm vendorForm;

    public ProductForm getProductForm() {
        return productForm;
    }

    public void setProductForm(ProductForm productForm) {
        this.productForm = productForm;
    }

    public VendorForm getVendorForm() {
        return vendorForm;
    }

    public void setVendorForm(VendorForm vendorForm) {
        this.vendorForm = vendorForm;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
