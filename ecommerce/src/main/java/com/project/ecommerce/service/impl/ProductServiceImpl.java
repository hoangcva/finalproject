package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
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
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @param productForm
     * @param vendorId
     * @return
     */
    @Override
    public Message addProduct(ProductForm productForm, Long vendorId) {
        Message result = new Message("", true);
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
            int categoryId = productForm.getCategoryId();
            int subCategoryId = productForm.getSubCategoryId();
            if (categoryId == 1) {
                productMapper.insertDetailCategory1(productForm);
            } else if (categoryId == 2) {
                if (subCategoryId == 1) {
                    productMapper.insertDetailCategory2Sub1(productForm);
                } else if (subCategoryId == 2) {
                    productMapper.insertDetailCategory2Sub2(productForm);
                } else if (subCategoryId == 3) {
                    productMapper.insertDetailCategory2Sub3(productForm);
                }
            } else if (categoryId == 3) {
                productMapper.insertDetailCategory3(productForm);
            }
            doUploadImage(productForm);
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_07_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_07_E));
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @return
     */
    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    /**
     * @return
     */
    @Override
    public List<SubCategoryDto> getALLSubCategory() {
        return categoryMapper.getALLSubCategory();
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
        ProductForm productDetail = productMapper.getProductDetailBaseOnCategory(productForm);
        productForm = setDetail(productDetail, productForm);
        List<ProductImageForm> productImageFormList = getProductImage(productForm.getProductId());
        productForm.setProductImageFormList(productImageFormList);
        return productForm ;
    }

    private ProductForm setDetail(ProductForm source, ProductForm target) {
        if (target.getCategoryId() == 1) {
            target.setSize(source.getSize());
            target.setMaterial(source.getMaterial());
            target.setColor(source.getColor());
        } else if (target.getCategoryId() == 2) {
            if (target.getSubCategoryId() == 1) {
                target.setModel(source.getModel());
                target.setType(source.getType());
                target.setFramePerSecond(source.getFramePerSecond());
                target.setWeight(source.getWeight());
                target.setSize(source.getSize());
                target.setConnectionPorts(source.getConnectionPorts());
                target.setVoltage(source.getVoltage());
            } else if (target.getSubCategoryId() == 2) {
                target.setModel(source.getModel());
                target.setCapacity(source.getCapacity());
                target.setPower(source.getPower());
                target.setVoltage(source.getVoltage());
            } else if (target.getSubCategoryId() == 3) {
                target.setModel(source.getModel());
                target.setCapacity(source.getCapacity());
                target.setPower(source.getPower());
                target.setVoltage(source.getVoltage());
                target.setSize(source.getSize());
            }
        } else if (target.getCategoryId() == 3) {
            target.setAuthor(source.getAuthor());
            target.setReleaseDate(source.getReleaseDate());
            target.setNumberOfPages(source.getNumberOfPages());
            target.setLanguage(source.getLanguage());
            target.setPublisher(source.getPublisher());
        }
        return target;
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

    @Override
    public List<CountriesDto> getCountries() {
        List<CountriesDto> countriesDtoList = productMapper.getCountries();
        return countriesDtoList;
    }

    private void doUploadImage(ProductForm productForm) {
        long productId = productForm.getProductId();
        MultipartFile imageFile1 = productForm.getUploadImage1().getUploadFile();
        MultipartFile imageFile2 = productForm.getUploadImage2().getUploadFile();
        MultipartFile imageFile3 = productForm.getUploadImage3().getUploadFile();
        List<ProductImageDto> productImageDtoList = productMapper.getProductImage(productId);

        if(!imageFile1.isEmpty()) {
            ProductImageDto productImageDto = productImageDtoList.stream()
                    .filter(productImage -> Consts.IMG_ORDER_1 == productImage.getImageOrder())
                    .findAny().orElse(null);
            if(productImageDto != null) {
                updateImage(imageFile1, productImageDto.getId());
            }
            else {
                saveImage(imageFile1, productId, Consts.IMG_ORDER_1);
            }
        } else if (productForm.getUploadImage1().getDelete() == "true"){
            //delete img
            deleteImage(productId, Consts.IMG_ORDER_1);
        }

        if(!imageFile2.isEmpty()) {
            ProductImageDto productImageDto = productImageDtoList.stream()
                    .filter(productImage -> Consts.IMG_ORDER_2 == productImage.getImageOrder())
                    .findAny().orElse(null);
            if(productImageDto != null) {
                updateImage(imageFile2, productImageDto.getId());
            } else {
                saveImage(imageFile2, productId, Consts.IMG_ORDER_2);
            }
        } else {
            //delete img
            deleteImage(productId, Consts.IMG_ORDER_2);
        }

        if(!imageFile3.isEmpty()) {
            ProductImageDto productImageDto = productImageDtoList.stream()
                    .filter(productImage -> Consts.IMG_ORDER_3 == productImage.getImageOrder())
                    .findAny().orElse(null);
            if(productImageDto != null) {
                updateImage(imageFile3, productImageDto.getId());
            } else {
                saveImage(imageFile3, productId, Consts.IMG_ORDER_3);
            }
        } else {
            //delete img
            deleteImage(productId, Consts.IMG_ORDER_3);
        }
    }

    private void saveImage(MultipartFile imageFile, long productId, int imageOrder) {
        // Tên file gốc tại Client.
        String name = StringUtils.cleanPath(imageFile.getOriginalFilename());
        if (name != null && name.length() > 0) {
            try {
                ProductImageDto productImage = new ProductImageDto();
                productImage.setName(name);
                productImage.setContent(imageFile.getBytes());
                productImage.setProductId(productId);
                productImage.setImageOrder(imageOrder);
                productMapper.saveProductImage(productImage);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    private void updateImage(MultipartFile imageFile, long id) {
        String name = StringUtils.cleanPath(imageFile.getOriginalFilename());
        if (name != null && name.length() > 0) {
            try {
                ProductImageDto productImage = new ProductImageDto();
                productImage.setId(id);
                productImage.setName(name);
                productImage.setContent(imageFile.getBytes());
                productMapper.updateProductImage(productImage);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    private void deleteImage(long productId, int imageOrder) {
        try {
            productMapper.removeProductImage(productId, imageOrder);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}
