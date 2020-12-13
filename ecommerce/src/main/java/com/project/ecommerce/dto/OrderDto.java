package com.project.ecommerce.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderDto implements Serializable {
    private static final long serialVersionUID = 8686410531140993726L;
    //product info
    //order info
    private long id;
    private Timestamp orderDate;
    private LocalDateTime deliveryDate;
    private String orderStatus;
    private Long shippingFee;
    private String orderDspId;
    private long billTotal;
    private String note;
    //customer info
    private long customerId;
    private String fullName;
    private String phoneNumber;
    private String deliveryAddress;


    public String getOrderDspId() {
        return orderDspId;
    }

    public void setOrderDspId(String orderDspId) {
        this.orderDspId = orderDspId;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public long getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(long billTotal) {
        this.billTotal = billTotal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
