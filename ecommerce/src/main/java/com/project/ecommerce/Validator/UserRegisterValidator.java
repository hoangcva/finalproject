package com.project.ecommerce.Validator;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserRegisterForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserRegisterValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserRegisterForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterForm userRegisterForm = (UserRegisterForm) target;
        Pattern regexUsername = Pattern.compile(Consts.REGEX_USERNAME);
        Pattern regexPassword = Pattern.compile(Consts.REGEX_PASSWORD);

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");

        if (!this.emailValidator.isValid(userRegisterForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (userRegisterForm.getId() == null) {
            UserDto userDto = userMapper.findUserByEmail(userRegisterForm.getEmail());
            if (userDto != null) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            UserDto userDto = userMapper.findUserByUserName(userRegisterForm.getUserName());
            if (!regexUsername.matcher(userRegisterForm.getUserName()).find()) {
                errors.rejectValue("userName", "Invalid.productForm.userName");
            } else if (userDto != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.UserForm.userName");
            }
        }
        if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmPassword")) {
            if (!regexPassword.matcher(userRegisterForm.getPassword()).find()) {
                errors.rejectValue("password", "Invalid.productForm.password");
                errors.rejectValue("confirmPassword", "");
            } else if (!userRegisterForm.getConfirmPassword().equals(userRegisterForm.getPassword())) {
                errors.rejectValue("password", "Match.UserForm.confirmPassword");
                errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
            }
        }
    }
}
