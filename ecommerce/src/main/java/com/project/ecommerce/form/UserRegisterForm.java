package com.project.ecommerce.form;

import java.io.Serializable;

public class UserRegisterForm implements Serializable {
    private static final long serialVersionUID = 5294594140356323101L;
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String confirmPassword;

    public UserRegisterForm() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void nullToEmpty() {
        this.fullName = this.fullName == null ? "" : this.fullName;
        this.email = this.email == null ? "" : this.email;
    }
}

