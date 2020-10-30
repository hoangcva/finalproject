package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.VendorProductDto;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public void addProduct(ProductForm productForm, Long vendorId) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            ProductDto productDto = new ProductDto( null,
                                                    productForm.getProductName(),
                                                    productForm.getDescription(),
                                                    productForm.getCategoryId(),
                                                    productForm.getSubCategoryId(),
                                                    productForm.getBrand(),
                                                    productForm.getSKU(),
                                                    productForm.getSize(),
                                                    productForm.getColor(),
                                                    productForm.getSubjectAge(),
                                                    productForm.getMaterial());

            productMapper.insertProduct(productDto);

            VendorProductDto vendorProductDto = new VendorProductDto( vendorId,
                                                                    productDto.getId(),
                                                                    productForm.getQuantity(),
                                                                    productForm.getPrice(),
                                                                    productForm.getRating());
            productMapper.insertVendorProduct(vendorProductDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
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
