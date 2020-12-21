package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.List;

public class FavoriteForm extends ProductForm {
    private static final long serialVersionUID = -121008663181448891L;
    private Long customerId;
    private Long favoriteId;

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
