package com.project.ecommerce.form;

import java.io.Serializable;

public class UserDeleteForm implements Serializable {
    private static final long serialVersionUID = -6097199568582846446L;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
