package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.VendorProductDto;
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
    void updateProduct(@Param("productId") Integer productId);
    void deleteProduct(@Param("productId") Integer productId);
    void insertVendorProduct(VendorProductDto vendorProductDto);
}
