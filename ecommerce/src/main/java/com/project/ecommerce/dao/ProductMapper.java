package com.project.ecommerce.dao;

import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorProductForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {
    void insertProduct(ProductDto productDto);
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    void updateProduct(ProductForm productForm);
    void updateVendorProduct(ProductForm productForm);
    void deleteProduct(@Param("productId") Long productId);
    void insertVendorProduct(VendorProductDto vendorProductDto);
    List<ProductForm> getAllProductByVendorId(@Param("vendorId") Long vendorId);
//    List<VendorProductDto> getAllVendorProductByVendorId(@Param("vendorId") Long vendorId);
    ProductForm getVendorProduct(@Param("productId") Long productId);

    List<ProductForm> getAllProduct(@Param("categoryId") Integer categoryId,
                                    @Param("subCategoryId") Integer subCategoryId,
                                    @Param("keyword") String keyword);

    CategoryDto findCategory(Integer  categoryId);
    SubCategoryDto findSubCategory(Integer  categoryId);
    ProductForm getProductDetail(@Param("productId") Long productId,
                                 @Param("vendorId") Long vendorId);

    List<VendorProductForm> getVendorListByProduct(@Param("productId") Long productId);

    void saveProductImage(ProductImageDto productImageDto);
}
