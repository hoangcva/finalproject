package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.List;

public class OrderForm implements Serializable {
    private static final long serialVersionUID = 326586143790672537L;
    //product info
    private CartInfoForm cartInfoForm;
    private List<OrderDetailForm> orderDetailList;
    //order info
    private String id;
    private String orderDateTime;
    private String orderStatus;
    private String note;
    private Long shippingFee;
    private long billTotal;
    private String orderDspId;
    //customer info
    private String fullName;
    private String phoneNumber;
    private String deliveryAddress;
    private Long addressId;
    private CustomerAddressForm addressForm;

    public List<OrderDetailForm> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailForm> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getOrderDspId() {
        return orderDspId;
    }

    public void setOrderDspId(String orderDspId) {
        this.orderDspId = orderDspId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

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

    public long getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(long billTotal) {
        this.billTotal = billTotal;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
