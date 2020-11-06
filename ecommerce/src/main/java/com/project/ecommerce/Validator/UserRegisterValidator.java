package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserRegisterForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.UserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.UserForm.gender");

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
            if (userDto != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.UserForm.userNam");
            }
        }
        if (!errors.hasErrors()) {
            if (!userRegisterForm.getConfirmPassword().equals(userRegisterForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.usersForm.confirmPassword");
            }
        }
    }
}
