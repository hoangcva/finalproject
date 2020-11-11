package com.project.ecommerce.form;

import java.io.Serializable;

public class CartLineInfo implements Serializable {
    private static final long serialVersionUID = -6735879961418173037L;
    private ProductForm productForm;
    private int quantity;
    public long getAmount() {
        return this.productForm.getPrice() * this.quantity;
    }

    public ProductForm getProductForm() {
        return productForm;
    }

    public void setProductForm(ProductForm productForm) {
        this.productForm = productForm;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
