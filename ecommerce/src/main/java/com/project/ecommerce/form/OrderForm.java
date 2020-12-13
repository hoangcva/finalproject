package com.project.ecommerce.form;

import java.io.Serializable;

public class OrderForm implements Serializable {
    private static final long serialVersionUID = 326586143790672537L;
    private CartInfoForm cartInfoForm;
    private String orderId;
    private String orderDateTime;
    private CustomerAddressForm addressForm;
    private long totalAmount;
    private String orderStatus;
    private Long addressId;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

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
