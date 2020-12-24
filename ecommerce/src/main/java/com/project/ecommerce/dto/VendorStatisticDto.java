package com.project.ecommerce.dto;

import java.io.Serializable;

public class VendorStatisticDto implements Serializable {
    private static final long serialVersionUID = -2312589855523204729L;
    private Long productIncome;
    private Long productId;
    private Long buyQuantity;
    private String productName;
    private byte[] content;

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
