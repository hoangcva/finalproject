package com.project.ecommerce.form;

import java.io.Serializable;

public class OrderForm implements Serializable {
    private static final long serialVersionUID = 326586143790672537L;
    private CartInfoForm cartInfoForm;
    private String orderId;
    private String orderDateTime;
    private CustomerAddressForm addressForm;

    public CartInfoForm getCartInfoForm() {
        return cartInfoForm;
    }

    public void setCartInfoForm(CartInfoForm cartInfoForm) {
        this.cartInfoForm = cartInfoForm;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public CustomerAddressForm getAddressForm() {
        return addressForm;
    }

    public void setAddressForm(CustomerAddressForm addressForm) {
        this.addressForm = addressForm;
    }
}
