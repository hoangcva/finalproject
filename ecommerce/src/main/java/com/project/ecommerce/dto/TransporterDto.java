package com.project.ecommerce.dto;

import org.apache.tomcat.jni.User;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransporterDto extends UserDto {
    private static final long serialVersionUID = -1177264913158729687L;
    private long transportedId;
    private long createdUser;
    private long updatedUser;
    private long shippingFee;
    private String description;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getTransportedId() {
        return transportedId;
    }

    public void setTransportedId(long transportedId) {
        this.transportedId = transportedId;
    }

    public long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(long createdUser) {
        this.createdUser = createdUser;
    }

    public long getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(long updatedUser) {
        this.updatedUser = updatedUser;
    }

    public long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
