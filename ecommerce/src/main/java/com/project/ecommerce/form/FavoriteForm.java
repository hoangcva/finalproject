package com.project.ecommerce.form;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.Serializable;
import java.util.List;

public class FavoriteForm extends ProductForm {
    private static final long serialVersionUID = -121008663181448891L;
    private Long customerId;
    private Long favoriteId;
    private byte[] thumbnail;

    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getThumbnail());
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
