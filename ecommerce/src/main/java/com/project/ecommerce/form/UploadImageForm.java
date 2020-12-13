package com.project.ecommerce.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadImageForm implements Serializable {
    private static final long serialVersionUID = -1711514096147713511L;
    private MultipartFile uploadFile;
    private String delete;

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }
}
