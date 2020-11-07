package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.VendorProductDto;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
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
    public void updateProduct(ProductForm productForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            productMapper.updateProduct(productForm);
            productMapper.updateVendorProduct(productForm);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        productMapper.deleteProduct(productId);
    }

    @Override
    public List<ProductForm> getAllProductByVendorId(Long vendorId) {
        return productMapper.getAllProductByVendorId(vendorId);
    }

    @Override
    public ProductForm getVendorProduct(Long productId) {
        return productMapper.getVendorProduct(productId);
    }

    @Override
    public List<ProductForm> getALlProduct(Integer categoryId, Integer subCategoryId) {
        return productMapper.getAllProduct(categoryId, subCategoryId);
    }

    @Override
    public List<CategoryForm> getCategory() {
        List<CategoryDto> categoryDtoList= getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = getALLSubCategory();

        List<CategoryForm> categoryForms = new ArrayList<>();
        List<SubCategoryForm> subCategoryForms = new ArrayList<>();

        for (CategoryDto categoryDto : categoryDtoList) {
            CategoryForm categoryForm = new CategoryForm();
            BeanUtils.copyProperties(categoryDto, categoryForm);
            categoryForms.add(categoryForm);
        }

        for (SubCategoryDto subCategoryDto : subCategoryDtoList) {
            SubCategoryForm subCategoryForm = new SubCategoryForm();
            BeanUtils.copyProperties(subCategoryDto, subCategoryForm);
            subCategoryForms.add(subCategoryForm);
        }

        for (CategoryForm categoryForm : categoryForms) {
            List<SubCategoryForm> tmpSubCategories = new ArrayList<>();
            for (SubCategoryForm subCategoryForm : subCategoryForms) {
                if (subCategoryForm.getCategoryId().equals(categoryForm.getId())) {
                    tmpSubCategories.add(subCategoryForm);
                }
            }
            categoryForm.setSubCategoryForms(tmpSubCategories);
        }

        return categoryForms;
    }

    @Override
    public List<ProductForm> getProduct(Integer categoryId, Integer supCategoryId) {
        categoryId = Consts.DEFAULT_VALUE_0.equals(categoryId) ? null : categoryId;
        supCategoryId = Consts.DEFAULT_VALUE_0.equals(supCategoryId) ? null : supCategoryId;
        return getALlProduct(categoryId, supCategoryId);
    }
}
