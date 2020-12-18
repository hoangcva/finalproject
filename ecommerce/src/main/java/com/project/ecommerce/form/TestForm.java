package com.project.ecommerce.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class TestForm implements Serializable {
    private static final long serialVersionUID = 2693800535754079916L;
    private byte[] description;
    private String text;
    private int id;
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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
