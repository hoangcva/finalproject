package com.project.ecommerce.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProductImageDto implements Serializable {
    private static final long serialVersionUID = 1283604084203867013L;
    private long id;
    private long productId;
    private String name;
    private byte[] content;
    private int imageOrder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }
}
