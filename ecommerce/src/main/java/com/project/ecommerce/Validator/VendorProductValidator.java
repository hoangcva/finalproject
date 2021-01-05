package com.project.ecommerce.Validator;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.ProductForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VendorProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProductForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductForm productForm = (ProductForm) target;
        int categoryId = productForm.getCategoryId();
        int subCategoryId = productForm.getSubCategoryId();

        if (Consts.ACTION_ADDEXTEND.equals(productForm.getAction()) || Consts.ACTION_UPDATEEXTEND.equals(productForm.getAction())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "NotEmpty.productForm.quantity");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");

            if (!errors.hasFieldErrors("price")) {
                if (productForm.getListPrice() < productForm.getPrice()) {
                    errors.rejectValue("price", "Greater.productForm.Price");
                } else if (productForm.getPrice() <= 0) {
                    errors.rejectValue("price", "Invalid.productForm.price");
                }
            }

            if (!errors.hasFieldErrors("quantity")) {
                if (productForm.getQuantity() <= 0) {
                    errors.rejectValue("quantity", "Invalid.productForm.quantity");
                }
            }

            return;
        }

        switch (categoryId) {
            case 1:
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "size", "NotEmpty.productForm.size");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "material", "NotEmpty.productForm.material");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "NotEmpty.productForm.color");
                break;
            case 2:
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "NotEmpty.productForm.model");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voltage", "NotEmpty.productForm.voltage");
                switch (subCategoryId) {
                    case 1:
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty.productForm.type");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "framePerSecond", "NotEmpty.productForm.framePerSecond");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "NotEmpty.productForm.weight");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "connectionPorts", "NotEmpty.productForm.connectionPorts");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "size", "NotEmpty.productForm.size");
                        break;
                    case 2:
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "NotEmpty.productForm.capacity");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "power", "NotEmpty.productForm.power");
                        break;
                    case 3:
//                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "power", "NotEmpty.productForm.power");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "NotEmpty.productForm.capacity");
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "size", "NotEmpty.productForm.size");
                        break;
                }
                break;
            case 3:
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "NotEmpty.productForm.author");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "releaseDate", "NotEmpty.productForm.releaseDate");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfPages", "NotEmpty.productForm.numberOfPages");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "language", "NotEmpty.productForm.language");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "publisher", "NotEmpty.productForm.publisher");
                break;
            default:
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "NotEmpty.productForm.productName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "NotEmpty.productForm.brand");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SKU", "NotEmpty.productForm.SKU");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "NotEmpty.productForm.quantity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listPrice", "NotEmpty.productForm.listPrice");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "origin", "NotEmpty.productForm.origin");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.productForm.description");

        if (!errors.hasFieldErrors("listPrice")) {
            if (productForm.getListPrice() < productForm.getPrice()) {
                errors.rejectValue("listPrice", "Greater.productForm.Price");
            }
        }

        if (!errors.hasFieldErrors("quantity")) {
            if (productForm.getQuantity() <= 0) {
                errors.rejectValue("quantity", "Invalid.productForm.quantity");
            }
        }

        if (!errors.hasFieldErrors("price")) {
            if (productForm.getPrice() <= 0) {
                errors.rejectValue("price", "Invalid.productForm.price");
            }
        }

        if (!errors.hasFieldErrors("listPrice")) {
            if (productForm.getListPrice() <= 0) {
                errors.rejectValue("listPrice", "Invalid.productForm.listPrice");
            }
        }
    }
}
