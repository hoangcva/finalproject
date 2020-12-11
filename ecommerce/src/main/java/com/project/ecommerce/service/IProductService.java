package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductImageDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductImageForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorProductForm;
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

    List<ProductForm> getAllProduct(@Nullable Integer categoryId, @Nullable Integer subCategoryId, String keyword);
    List<CategoryForm> getCategory();
    List<ProductForm> getProducts(Integer categoryId, Integer supCategoryId, String keyword);
    ProductForm getProductDetail(Long productId, Long vendorId);

    CategoryDto findCategory(Integer  categoryId);
    SubCategoryDto findSubCategory(Integer  categoryId);
    List<VendorProductForm> getVendorListByProduct(Long productId);

    void saveProductImage(List<ProductImageDto> productImageDtoList);
    List<ProductImageForm> getProductImage(long productId);
}
