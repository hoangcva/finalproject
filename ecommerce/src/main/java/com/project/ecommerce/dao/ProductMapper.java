package com.project.ecommerce.dao;

import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.ProductImageForm;
import com.project.ecommerce.form.VendorProductForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {
    void insertProduct(ProductDto productDto);
    void updateProduct(ProductForm productForm);
    void updateVendorProduct(ProductForm productForm);
    void deleteProduct(@Param("productId") long productId);
    void insertVendorProduct(VendorProductDto vendorProductDto);
    List<ProductForm> getAllProductByVendorId(@Param("vendorId") Long vendorId);
//    List<VendorProductDto> getAllVendorProductByVendorId(@Param("vendorId") Long vendorId);
    ProductForm getVendorProduct(@Param("productId") long productId);
    Long getProductQuantity(@Param("productId") long productId, @Param("vendorId") long vendorId);

    List<ProductForm> getAllProduct(@Param("categoryId") Integer categoryId,
                                    @Param("subCategoryId") Integer subCategoryId,
                                    @Param("keyword") String keyword);


    ProductForm getProductDetail(@Param("productId") long productId,
                                 @Param("vendorId") long vendorId);

    List<VendorProductForm> getVendorListByProduct(@Param("productId") Long productId);

    void saveProductImage(ProductImageDto productImageDto);
    List<ProductImageDto> getProductImage(@Param("productId") long productId);
    void updateProductImage(ProductImageDto productImageDto);
    void removeProductImage(@Param("productId") long productId, @Param("imageOrder") long imageOrder);

    boolean updateProductQuantity(@Param("productId") long productId, @Param("vendorId") long vendorId, @Param("quantity") long quantity);

    List<CountriesDto> getCountries();

    boolean insertDetailCategory1(ProductForm productForm);
    boolean insertDetailCategory2Sub1(ProductForm productForm);
    boolean insertDetailCategory2Sub2(ProductForm productForm);
    boolean insertDetailCategory2Sub3(ProductForm productForm);
    boolean insertDetailCategory3(ProductForm productForm);

    ProductForm getProductDetailBaseOnCategory(ProductForm productForm);
}
