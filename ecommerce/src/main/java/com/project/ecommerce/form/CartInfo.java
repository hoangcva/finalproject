package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartInfo implements Serializable {
    private static final long serialVersionUID = 1409178486466589445L;
    private int orderNum;
    private final List<CartLineInfo> cartLines = new ArrayList<>();

    private CartLineInfo findLineByCode(Long productId) {
        for (CartLineInfo cartLine : this.cartLines) {
            if(cartLine.getProductForm().getProductId().equals(productId)) {
                return  cartLine;
            }
        }
        return null;
    }

    public void removeProduct(ProductForm productForm) {
        CartLineInfo cartLine = this.findLineByCode(productForm.getProductId());
        if (cartLine != null) {
            this.cartLines.remove(cartLine);
        }
    }

    public void addCartLine(ProductForm productForm, int quantity) {
        CartLineInfo cartLine = this.findLineByCode(productForm.getProductId());
        if (cartLine == null) {
            cartLine = new CartLineInfo();
            cartLine.setProductForm(productForm);
            cartLine.setQuantity(quantity);
            this.cartLines.add(cartLine);
        } else {
            int newQuantity = cartLine.getQuantity() + quantity;
            if (newQuantity <= 0) {
                this.cartLines.remove(cartLine);
            } else {
                cartLine.setQuantity(newQuantity);
            }
        }
    }

    public void updateCartLine(ProductForm productForm, int quantity) {
        addCartLine(productForm, quantity);
    }

    public long getAmountTotal() {
        long total = 0;
        for (CartLineInfo cartLine : this.cartLines) {
            total += cartLine.getAmount();
        }
        return total;
    }
}
