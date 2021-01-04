package com.project.ecommerce.Validator;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.form.VendorForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

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
        Pattern regexUsername = Pattern.compile(Consts.REGEX_USERNAME);
        Pattern regexPassword = Pattern.compile(Consts.REGEX_PASSWORD);

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.VendorForm.phoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessCode", "NotEmpty.VendorForm.businessCode");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.VendorForm.fullName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressDetail", "NotEmpty.VendorForm.addressDetail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.VendorForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendorName", "NotEmpty.VendorForm.vendorName");
        Long vendorId = vendorForm.getVendorId();
        if (Consts.ACTION_REGISTER.equals(vendorForm.getAction())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
            if (!errors.hasFieldErrors("userName")) {
                int count = userMapper.findVendorExist(vendorForm.getUserName(),null,null, vendorId);
                if (!regexUsername.matcher(vendorForm.getUserName()).find()) {
                    errors.rejectValue("userName", "Invalid.productForm.userName");
                } else if (count > 0) {
                    // Tên tài khoản đã bị sử dụng bởi người khác.
                    errors.rejectValue("userName", "Duplicate.UserForm.userName");
                }
            }
            if (!errors.hasFieldErrors("businessCode")) {
                int count = userMapper.findVendorExist(null,null, vendorForm.getBusinessCode(), vendorId);
                if (count > 0) {
                    // Tên tài khoản đã bị sử dụng bởi người khác.
                    errors.rejectValue("businessCode", "Duplicate.VendorForm.businessCode");
                }
            }
            if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmPassword")) {
                if (!regexPassword.matcher(vendorForm.getPassword()).find()) {
                    errors.rejectValue("password", "Invalid.productForm.password");
                    errors.rejectValue("confirmPassword", "");
                } else if (!vendorForm.getConfirmPassword().equals(vendorForm.getPassword())) {
                    errors.rejectValue("password", "Match.UserForm.confirmPassword");
                    errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
                }
            }
        }

        if (!this.emailValidator.isValid(vendorForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (vendorForm.getId() == null) {
            int count = userMapper.findVendorExist(null,vendorForm.getEmail(), null, vendorId);
            if (count > 0) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }
    }
}
