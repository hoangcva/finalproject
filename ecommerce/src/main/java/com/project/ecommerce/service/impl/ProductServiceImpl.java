package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductService
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    /**
     * @param productForm
     * @param vendorId
     */
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
                                                    productForm.getOrigin(),
                                                    vendorId,
                                                    productForm.getListPrice());

            productMapper.insertProduct(productDto);
            productForm.setProductId(productDto.getId());
            VendorProductDto vendorProductDto = new VendorProductDto( vendorId,
                                                                    productDto.getId(),
                                                                    productForm.getQuantity(),
                                                                    productForm.getPrice(),
                                                                    productForm.getRating(),
                                                                    productForm.getSize(),
                                                                    productForm.getColor(),
                                                                    productForm.getSubjectAge(),
                                                                    productForm.getMaterial());
            productMapper.insertVendorProduct(vendorProductDto);
            doUploadImage(productForm);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    private void doUploadImage(ProductForm productForm) {
        MultipartFile[] imageFiles = productForm.getUploadFiles();
        for (MultipartFile image : imageFiles) {
            // Tên file gốc tại Client.
            String name = StringUtils.cleanPath(image.getOriginalFilename());
            if (name != null && name.length() > 0) {
                try {
                    ProductImageDto productImage = new ProductImageDto();
                    productImage.setName(name);
                    productImage.setContent(image.getBytes());
                    productImage.setProductId(productForm.getProductId());
                    productMapper.saveProductImage(productImage);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    /**
     * @return
     */
    @Override
    public List<CategoryDto> getAllCategory() {
        return productMapper.getAllCategory();
    }

    /**
     * @return
     */
    @Override
    public List<SubCategoryDto> getALLSubCategory() {
        return productMapper.getALLSubCategory();
    }

    /**
     * @param productForm
     */
    @Override
    public void updateProduct(ProductForm productForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            productMapper.updateProduct(productForm);
            productMapper.updateVendorProduct(productForm);
            doUploadImage(productForm);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    /**
     * @param productId
     */
    @Override
    public void deleteProduct(Long productId) {
        productMapper.deleteProduct(productId);
    }

    /**
     * @param vendorId
     * @return
     */
    @Override
    public List<ProductForm> getAllProductByVendorId(Long vendorId) {
        List<ProductForm> productFormList = productMapper.getAllProductByVendorId(vendorId);
        for (ProductForm productForm : productFormList) {
            List<ProductImageForm> productImageFormList = getProductImage(productForm.getProductId());
            productForm.setProductImageFormList(productImageFormList);
        }
        return productFormList;
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public ProductForm getVendorProduct(Long productId) {
        ProductForm productForm = productMapper.getVendorProduct(productId);
        List<ProductImageForm> productImageFormList = getProductImage(productForm.getProductId());
        productForm.setProductImageFormList(productImageFormList);
        return productForm;
    }

    /**
     * @param categoryId
     * @param subCategoryId
     * @param keyword
     * @return
     */
    @Override
    public List<ProductForm> getAllProduct(Integer categoryId, Integer subCategoryId, String keyword) {
        List<ProductForm> productFormList = productMapper.getAllProduct(categoryId, subCategoryId, keyword);
        for (ProductForm productForm : productFormList) {
            List<ProductImageForm> productImageFormList = getProductImage(productForm.getProductId());
            productForm.setProductImageFormList(productImageFormList);
        }
        return productFormList;
    }

    /**
     * @return
     */
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

    /**
     * @param categoryId
     * @param supCategoryId
     * @param keyword
     * @return
     */
    @Override
    public List<ProductForm> getProducts(Integer categoryId, Integer supCategoryId, String keyword) {
        categoryId = Consts.DEFAULT_VALUE_0.equals(categoryId) ? null : categoryId;
        supCategoryId = Consts.DEFAULT_VALUE_0.equals(supCategoryId) ? null : supCategoryId;
        if(keyword == null || keyword.isEmpty()) {
            keyword = null;
        } else {
            keyword = '%' + keyword + '%';
        }
        return getAllProduct(categoryId, supCategoryId, keyword);
    }

    /**
     * @param productId
     * @param vendorId
     * @return
     */
    @Override
    public ProductForm getProductDetail(Long productId, Long vendorId) {
        ProductForm productForm = productMapper.getProductDetail(productId, vendorId);
        List<ProductImageForm> productImageFormList = getProductImage(productForm.getProductId());
        productForm.setProductImageFormList(productImageFormList);
        return productForm;
    }

    @Override
    public CategoryDto findCategory(Integer categoryId) {
        return null;
    }

    @Override
    public SubCategoryDto findSubCategory(Integer categoryId) {
        return null;
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public List<VendorProductForm> getVendorListByProduct(Long productId) {
        return productMapper.getVendorListByProduct(productId);
    }

    /**
     * @param productImageDtoList
     */
    @Override
    public void saveProductImage(List<ProductImageDto> productImageDtoList) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            for (ProductImageDto productImage : productImageDtoList) {
                productMapper.saveProductImage(productImage);
            }
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    @Override
    public List<ProductImageForm> getProductImage(long productId) {
        List<ProductImageDto> productImageDtoList = productMapper.getProductImage(productId);
        List<ProductImageForm> productImageFormList = new ArrayList<>();
        for (ProductImageDto productImageDto : productImageDtoList) {
            ProductImageForm productImageForm = new ProductImageForm();
            BeanUtils.copyProperties(productImageDto, productImageForm);
            productImageFormList.add(productImageForm);
        }
        return productImageFormList;
    }
}
