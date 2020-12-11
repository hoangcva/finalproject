package com.project.ecommerce.form;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ProductImageForm implements Serializable {
    private static final long serialVersionUID = -8134351929014169401L;
    private byte[] content;
    private String name;
    private long productId;
    private long id;

    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getContent());
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
