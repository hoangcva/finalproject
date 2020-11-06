package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.form.VendorForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VendorRegisterValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == VendorForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        VendorForm vendorForm = (VendorForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.VendorForm.phoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessCode", "NotEmpty.VendorForm.businessCode");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.VendorForm.fullName");

        if (!this.emailValidator.isValid(vendorForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (vendorForm.getId() == null) {
            int count = userMapper.findVendorExist(null,vendorForm.getEmail(), null);
            if (count > 0) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.VendorForm.email");
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            int count = userMapper.findVendorExist(vendorForm.getUserName(),null,null);
            if (count > 0) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.UserForm.userName");
            }
        }
        if (!errors.hasFieldErrors("businessCode")) {
            int count = userMapper.findVendorExist(null,null, vendorForm.getBusinessCode());
            if (count > 0) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("businessCode", "Duplicate.VendorForm.businessCode");
            }
        }
        if (!errors.hasErrors()) {
            if (!vendorForm.getConfirmPassword().equals(vendorForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
            }
        }
    }
}
