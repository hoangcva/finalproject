package com.project.ecommerce.Validator;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.ProductForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProductForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductForm productForm = (ProductForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "NotEmpty.productForm.productName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "NotEmpty.productForm.brand");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SKU", "NotEmpty.productForm.SKU");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "NotEmpty.productForm.quantity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listPrice", "NotEmpty.productForm.listPrice");


        if (!errors.hasFieldErrors("price")) {
            if (productForm.getListPrice() > productForm.getPrice()) {
                errors.rejectValue("listPrice", "Greater.productForm.Price");
            }
        }
    }
}
