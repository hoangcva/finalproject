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
    void updateProduct(ProductForm productForm);
    void updateVendorProduct(ProductForm productForm);
    void deleteProduct(@Param("productId") long productId);
    void insertVendorProduct(VendorProductDto vendorProductDto);
    List<ProductForm> getAllProductByVendorId(@Param("vendorId") Long vendorId);
//    List<VendorProductDto> getAllVendorProductByVendorId(@Param("vendorId") Long vendorId);
    ProductForm getVendorProduct(@Param("productId") long productId,@Param("vendorId") long vendorId);
    Long getProductQuantity(@Param("productId") long productId, @Param("vendorId") long vendorId);

    List<ProductForm> getAllProduct(@Param("categoryId") Integer categoryId,
                                    @Param("subCategoryId") Integer subCategoryId,
                                    @Param("keyword") String keyword,
                                    @Param("enable") Boolean enable,
                                    @Param("vendorId")  Long vendorId);

    ProductForm getProductDetail(@Param("productId") long productId,
                                 @Param("vendorId") long vendorId);
    ProductForm getProductDetailExtend(@Param("productId") long productId);

    List<VendorProductForm> getVendorListByProduct(@Param("productId") Long productId);

    void saveProductImage(ProductImageDto productImageDto);
    List<ProductImageDto> getProductImage(@Param("productId") long productId);
    ProductImageDto getProductCover(@Param("productId") long productId);
    void updateProductImage(ProductImageDto productImageDto);
    void removeProductImage(@Param("productId") long productId, @Param("imageOrder") long imageOrder);

    boolean updateProductQuantity(@Param("productId") long productId, @Param("vendorId") long vendorId, @Param("quantity") long quantity);

    List<CountriesDto> getCountries();

    void insertDetailCategory1(ProductForm productForm);
    void insertDetailCategory2(ProductForm productForm);
    void insertDetailCategory2Sub1(ProductForm productForm);
    void insertDetailCategory2Sub2(ProductForm productForm);
    void insertDetailCategory2Sub3(ProductForm productForm);
    void insertDetailCategory3(ProductForm productForm);

    void updateDetailCategory1(ProductForm productForm);
    void updateDetailCategory2(ProductForm productForm);
    void updateDetailCategory2Sub1(ProductForm productForm);
    void updateDetailCategory2Sub2(ProductForm productForm);
    void updateDetailCategory2Sub3(ProductForm productForm);
    void updateDetailCategory3(ProductForm productForm);

    ProductForm getProductDetailBaseOnCategory(ProductForm productForm);

    void activateProduct(VendorProductForm vendorProductForm);
    void activateVendorProduct(VendorProductForm vendorProductForm);

    List<ProductForm> getAllProductMainPage(@Param("categoryId") Integer categoryId,
                                            @Param("subCategoryId") Integer subCategoryId,
                                            @Param("keyword") String keyword);

    void saveRating(@Param("productId") Long productId,
                    @Param("vendorId") Long vendorId,
                    @Param("rating") Float rating);

    List<ProductForm> getTop10NewestProduct(@Param("categoryId") Integer categoryId,
                                            @Param("subCategoryId") Integer subCategoryId,
                                            @Param("keyword") String keyword);

}
