package com.project.ecommerce.Validator;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.RegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserUpdateForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateForm userUpdateForm = (UserUpdateForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.UserForm.fullname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.UserForm.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "NotEmpty.UserForm.province");

        if (!this.emailValidator.isValid(userUpdateForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (userUpdateForm.getId() == null) {
            UserDto userDto = userMapper.findUserByEmail(userUpdateForm.getEmail());
            if (userDto != null && !userDto.getUserName().equals(userUpdateForm.getUserName())) {
                // Email đã được sử dụng bởi tài khoản khác.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }
    }
}
