package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartInfoForm implements Serializable {
    private static final long serialVersionUID = 1409178486466589445L;
    private int orderNum;
    private final List<CartLineInfoForm> cartLines = new ArrayList<>();

    private CartLineInfoForm findLineByCode(Long productId) {
        for (CartLineInfoForm cartLine : this.cartLines) {
            if(cartLine.getProductForm().getProductId().equals(productId)) {
                return  cartLine;
            }
        }
        return null;
    }

    public void removeProduct(ProductForm productForm) {
        CartLineInfoForm cartLine = this.findLineByCode(productForm.getProductId());
        if (cartLine != null) {
            this.cartLines.remove(cartLine);
        }
    }

    public void addCartLine(ProductForm productForm, long quantity) {
        CartLineInfoForm cartLine = this.findLineByCode(productForm.getProductId());
        if (cartLine == null) {
            cartLine = new CartLineInfoForm();
            cartLine.setProductForm(productForm);
            cartLine.setBuyQuantity(quantity);
            this.cartLines.add(cartLine);
        } else {
            long newQuantity = cartLine.getBuyQuantity() + quantity;
            if (newQuantity <= 0) {
                this.cartLines.remove(cartLine);
            } else {
                cartLine.setBuyQuantity(newQuantity);
            }
        }
    }

    public void updateCartLine(ProductForm productForm, int quantity) {
        addCartLine(productForm, quantity);
    }

    public long getBillTotal() {
        long total = 0;
        for (CartLineInfoForm cartLine : this.cartLines) {
            total += cartLine.getAmount();
        }
        return total;
    }

    public int getQuantityTotal() {
        int total = 0;
        for (CartLineInfoForm cartLine : this.cartLines) {
            total += cartLine.getBuyQuantity();
        }
        return total;
    }

    public long getListPriceTotal() {
        long total = 0;
        for (CartLineInfoForm cartLine : this.cartLines) {
            total += cartLine.getProductForm().getListPrice();
        }
        return total;
    }

    public List<CartLineInfoForm> getCartLines() {
        return cartLines;
    }
}
