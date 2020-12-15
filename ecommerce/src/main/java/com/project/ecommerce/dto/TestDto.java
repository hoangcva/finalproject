package com.project.ecommerce.dto;

import java.io.Serializable;

public class TestDto implements Serializable {
    private static final long serialVersionUID = -8409920823109155929L;
    private int id;
    private byte[] description;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }
}
