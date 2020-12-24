package com.project.ecommerce.form;

import java.io.Serializable;

public class VendorStatisticForm implements Serializable {
    private static final long serialVersionUID = -8162914317344406449L;
    private Long productIncome;
    private Long productId;
    private Long buyQuantity;
    private String productName;
    private byte[] content;
    private Long totalIncome;
    private Long incomeLast30Days;
    private Long todayIncome;

    public Long getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(Long todayIncome) {
        this.todayIncome = todayIncome;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getIncomeLast30Days() {
        return incomeLast30Days;
    }

    public void setIncomeLast30Days(Long incomeLast30Days) {
        this.incomeLast30Days = incomeLast30Days;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProductIncome() {
        return productIncome;
    }

    public void setProductIncome(Long productIncome) {
        this.productIncome = productIncome;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Long buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
