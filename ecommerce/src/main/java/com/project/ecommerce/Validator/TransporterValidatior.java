package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.form.UserRegisterForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TransporterValidatior implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == TransporterForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransporterForm transporterForm = (TransporterForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.UserForm.fullName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.TransporterForm.phoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.TransporterForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shippingFee", "NotEmpty.TransporterForm.shippingFee");

        if (!errors.hasFieldErrors("email")) {
            if (!this.emailValidator.isValid(transporterForm.getEmail())) {
                // Email không hợp lệ.
                errors.rejectValue("email", "Pattern.UserForm.email");
            } else if (transporterForm.getId() == null) {
                UserDto userDto = userMapper.findUserByEmail(transporterForm.getEmail());
                if (userDto != null) {
                    // Email đã được sử dụng bởi tài khoản khác.
                    errors.rejectValue("email", "Duplicate.UserForm.email");
                }
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            UserDto userDto = userMapper.findUserByUserName(transporterForm.getUserName());
            if (userDto != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.UserForm.userName");
            }
        }
        if (!errors.hasErrors()) {
            if (!transporterForm.getConfirmPassword().equals(transporterForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.usersForm.confirmPassword");
            }
        }
    }
}
