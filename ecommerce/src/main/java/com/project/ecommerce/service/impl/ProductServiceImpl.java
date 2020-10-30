package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(ProductDto productDto) {

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return productMapper.getAllCategory();
    }

    @Override
    public List<SubCategoryDto> getALLSubCategory() {
        return productMapper.getALLSubCategory();
    }

    @Override
    public void updateProduct(Integer productId) {

    }

    @Override
    public void deleteProduct(Integer productId) {

    }
}
