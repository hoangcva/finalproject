package com.project.ecommerce.controller.admin;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.form.AdminProductForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorProductForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/product")
public class AdminProductController {
    @Autowired
    private IProductService productService;

    @PostMapping(value = "/activate")
    public String activateProductAdmin(Model model,
                                       @RequestBody AdminProductForm adminProductForm,
                                       Authentication auth) {

        VendorProductForm vendorProductForm = new VendorProductForm();
        vendorProductForm.setProductId(adminProductForm.getProductId());
        vendorProductForm.setVendorId(adminProductForm.getVendorId());
        vendorProductForm.setEnable(adminProductForm.getEnable());
        Message result = productService.activateProduct(vendorProductForm);
        if (result.isSuccess() == false) {
            model.addAttribute("message", result.getMessage());
            model.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }
        String type = adminProductForm.getRadioType();
        Integer categoryId = Integer.valueOf(0).equals(adminProductForm.getCategoryId()) ? null : adminProductForm.getCategoryId();
        Integer subCategoryId = Integer.valueOf(0).equals(adminProductForm.getSubCategoryId()) ? null : adminProductForm.getSubCategoryId();
        Boolean status = (type == null || "all".equals(type)) ? null : ("active".equals(type) ? true : false);
        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, adminProductForm.getKeyword(), status, null);

        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
        model.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-product-admin";
    }

    @GetMapping(value = "/view/list/search")
    public String searchProductAdmin(HttpServletRequest request, Model model,
                                     @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                     @RequestParam(value = "subCategoryId", required = false) Integer subCategoryId,
                                     @RequestParam(value = "radioType", required = false) String type,
                                     ModelMap modelMap,
                                     Authentication auth) {
        categoryId = Integer.valueOf(0).equals(categoryId) ? null : categoryId;
        subCategoryId = Integer.valueOf(0).equals(subCategoryId) ? null : subCategoryId;
        Boolean enable = (type == null || "all".equals(type)) ? null : ("active".equals(type) ? true : false);
        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, keyword, enable, null);

        if (productFormList.size() == 0) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-product-admin";
    }

    @GetMapping(value = "/view/list")
    public String showAllProductAdmin(Model model) {
        List<ProductForm> productFormList = productService.getProducts(null, null, null, null, null);
        List<CategoryDto> categoryDtoList = productService.getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(0);
        categoryDto.setName("All");
        categoryDtoList.add(0, categoryDto);
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setCategoryId(0);
        subCategoryDto.setId(0);
        subCategoryDto.setName("ALL");
        subCategoryDtoList.add(0, subCategoryDto);
        ProductForm productForm = new ProductForm();
        productForm.setCategoryId(0);
        productForm.setSubCategoryId(0);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
        model.addAttribute("productForm", productForm);
        return "/admin/allProduct";
    }

    @GetMapping(value = "/vendor/view")
    public String getAllProductByVendorIdForAdmin(Model model,
                                                  Authentication auth,
                                                  @ModelAttribute("vendorId") Long vendorId) {
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "admin/listVendorProduct";
    }
}
