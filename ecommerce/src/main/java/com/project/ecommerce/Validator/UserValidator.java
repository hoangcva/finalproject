package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.RegisterForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == RegisterForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm registerForm = (RegisterForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.UserForm.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "NotEmpty.UserForm.province");

        if (!this.emailValidator.isValid(registerForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (registerForm.getId() == null) {
            UserDto userDto = userMapper.findUserByEmail(registerForm.getEmail());
            if (userDto != null) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }

        if (!errors.hasFieldErrors("userName")) {
            UserDto userDto = userMapper.findUserByUserName(registerForm.getUserName());
            if (userDto != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.UserForm.userNam");
            }
        }
        if (!errors.hasErrors()) {
            if (!registerForm.getConfirmPassword().equals(registerForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.usersForm.confirmPassword");
            }
        }
    }
}
