package com.project.ecommerce.Validator;

import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.form.SubCategoryForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == SubCategoryForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.SubCategoryForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.SubCategoryForm.description");
    }
}
