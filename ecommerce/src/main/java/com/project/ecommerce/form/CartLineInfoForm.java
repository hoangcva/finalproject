package com.project.ecommerce.form;

import java.io.Serializable;

public class CartLineInfoForm implements Serializable {
    private static final long serialVersionUID = -6735879961418173037L;
    private long id;
    private ProductForm productForm;
    private long buyQuantity;

    public CartLineInfoForm() {
    }

    public CartLineInfoForm(long id, ProductForm productForm, long buyQuantity) {
        this.id = id;
        this.productForm = productForm;
        this.buyQuantity = buyQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return this.productForm.getPrice() * this.buyQuantity;
    }

    public ProductForm getProductForm() {
        return productForm;
    }

    public void setProductForm(ProductForm productForm) {
        this.productForm = productForm;
    }

    public long getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(long buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
}
