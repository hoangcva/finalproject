package com.project.ecommerce.form;

public class CartLineInfo {
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
