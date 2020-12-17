package com.project.ecommerce.form;

public class TransporterForm extends UserRegisterForm {
    private static final long serialVersionUID = 2283551327108612344L;
    private long transportedId;
    private long createdUser;
    private long updatedUser;
    private Long shippingFee;
    private String description;
    private boolean isSubmitted;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
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

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
