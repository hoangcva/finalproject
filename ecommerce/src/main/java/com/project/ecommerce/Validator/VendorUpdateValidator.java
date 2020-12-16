package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VendorUpdateValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserUpdateForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        VendorForm vendorForm = (VendorForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");

        if (!this.emailValidator.isValid(vendorForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (vendorForm.getId() == null) {
            UserDto userDto = userMapper.findUserByEmail(vendorForm.getEmail());
            if (userDto != null && !userDto.getUserName().equals(vendorForm.getUserName())) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }
    }
}
