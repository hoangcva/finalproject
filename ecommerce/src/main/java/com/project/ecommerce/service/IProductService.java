package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.CountriesDto;
import com.project.ecommerce.dto.ProductImageDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductImageForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorProductForm;
import com.project.ecommerce.util.Message;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IProductService {
    Message addProduct(ProductForm productForm, Long vendorId);
    Message addProductExtend(ProductForm productForm, Long id);
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    Message updateProduct(ProductForm productForm);
    void deleteProduct(Long productId);
    List<ProductForm> getAllProductByVendorId(Long vendorId);
    ProductForm getVendorProduct(Long productId, Long vendorId);

    List<ProductForm> getAllProduct(@Nullable Integer categoryId, @Nullable Integer subCategoryId, String keyword, Boolean enable);
    List<CategoryForm> getCategory();
    List<ProductForm> getProducts(Integer categoryId, Integer supCategoryId, String keyword, Boolean enable);
    ProductForm getProductDetail(Long productId, Long vendorId);
    ProductForm getProductDetailExtend(Long productId);

    CategoryDto findCategory(Integer categoryId);
    SubCategoryDto findSubCategory(Integer subCategoryId);
    List<VendorProductForm> getVendorListByProduct(Long productId);

    void saveProductImage(List<ProductImageDto> productImageDtoList);
    List<ProductImageForm> getProductImage(long productId);
    ProductImageForm getProductCover(long productId);
    List<CountriesDto> getCountries();
    Message activateProduct(VendorProductForm vendorProductForm);
    Message activateVendorProduct(Long vendorId, Boolean enable);
    Message saveRating(Long productId, Long vendorId);
}
