package com.project.ecommerce.util;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -6143969263279152590L;
    private String message;
    private boolean isSuccess;

    public Message(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
