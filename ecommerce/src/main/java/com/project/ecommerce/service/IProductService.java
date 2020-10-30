package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.ProductForm;

import java.util.List;

public interface IProductService {
    void addProduct(ProductForm productForm, Long vendorId);
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    void updateProduct(Integer productId);
    void deleteProduct(Integer productId);
}
