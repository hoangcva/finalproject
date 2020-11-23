package com.project.ecommerce.Validator;

import com.project.ecommerce.form.CustomerAddressForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddressValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == CustomerAddressForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.UserUpdateForm.fullname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty.UserUpdateForm.phoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detail", "NotEmpty.UserUpdateForm.detail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "NotEmpty.UserUpdateForm.province");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "district", "NotEmpty.UserUpdateForm.district");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ward", "NotEmpty.UserUpdateForm.ward");
    }
}
