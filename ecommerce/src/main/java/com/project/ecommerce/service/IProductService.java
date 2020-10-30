package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.SubCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    void addProduct(ProductDto productDto);
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    void updateProduct(Integer productId);
    void deleteProduct(Integer productId);
}
