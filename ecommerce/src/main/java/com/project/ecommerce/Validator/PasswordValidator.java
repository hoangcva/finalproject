package com.project.ecommerce.Validator;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.PasswordForm;
import com.project.ecommerce.form.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements Validator {
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private BCryptPasswordEncoder encoder;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == PasswordForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern regexPassword = Pattern.compile(Consts.REGEX_PASSWORD);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        PasswordForm passwordForm = (PasswordForm) target;
        String currentPassword = userMapper.findUserById(passwordForm.getUserId()).getPassword();
        String oldPassword = passwordForm.getOldPassword();
        String newPassword = passwordForm.getNewPassword();
        String confirmPassword = passwordForm.getConfirmPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty.UserForm.newPassWord");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "NotEmpty.UserForm.currentPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");
        if (!errors.hasFieldErrors("oldPassword") && !encoder.matches(oldPassword,currentPassword)) {
            errors.rejectValue("oldPassword", "Match.UserForm.current");
        }

        if (!errors.hasErrors()) {
            if (!regexPassword.matcher(newPassword).find()) {
                errors.rejectValue("newPassword", "Invalid.productForm.password");
                errors.rejectValue("confirmPassword", "Empty");
            } else if (encoder.matches(newPassword,currentPassword)) {
                errors.rejectValue("newPassword", "Duplicate.UserForm.password");
                errors.rejectValue("confirmPassword","Empty");
            } else if (!newPassword.equals(confirmPassword)) {
                errors.rejectValue("newPassword", "Match.UserForm.confirmPassword");
                errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
            }
        }

//        if (!errors.hasErrors() && !newPassword.equals(confirmPassword)) {
//            errors.rejectValue("newPassword", "Match.UserForm.confirmPassword");
//            errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
//        }
//
//        if (!errors.hasErrors() &&  encoder.matches(newPassword,currentPassword)) {
//            errors.rejectValue("newPassword", "Duplicate.UserForm.password");
//            errors.rejectValue("confirmPassword","");
//        }
    }
}
