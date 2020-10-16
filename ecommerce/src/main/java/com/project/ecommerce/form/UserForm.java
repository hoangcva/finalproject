package com.project.ecommerce.form;

public class UserForm {
    private Long id;

    private String userName;
    private String password;
    private String fullName;
    private Boolean enable;
    private String gender;
    private String email;
    private String confirmPassword;
    private String city;

    public UserForm() {
    }

    public UserForm(Long id, String userName, String password, String fullName, Boolean enable,
                    String gender, String email, String confirmPassword, String city) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.enable = enable;
        this.gender = gender;
        this.email = email;
        this.confirmPassword = confirmPassword;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}

