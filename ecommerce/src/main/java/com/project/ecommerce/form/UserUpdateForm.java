package com.project.ecommerce.form;

import java.io.Serializable;

public class UserUpdateForm implements Serializable {
    private static final long serialVersionUID = 8240543896216842926L;
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private Boolean enable;
    private String gender;
    private String email;
    private String confirmPassword;
    private String province;

    public UserUpdateForm() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
        this.gender = this.fullName == null ? "" : this.fullName;
        this.email = this.email == null ? "" : this.email;
        this.province = this.province == null ? "" : this.province;
    }

    public boolean isSelected(String province) {
        return this.province.equals(province);
    }
}

