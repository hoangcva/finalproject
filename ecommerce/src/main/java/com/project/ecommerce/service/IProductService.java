package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.ProductForm;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IProductService {
    void addProduct(ProductForm productForm, Long vendorId);
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    void updateProduct(ProductForm productForm);
    void deleteProduct(Long productId);
    List<ProductForm> getAllProductByVendorId(Long vendorId);
    ProductForm getVendorProduct(Long productId);

    List<ProductForm> getALlProduct(@Nullable Integer categoryId, @Nullable Integer subCategoryId);
}
