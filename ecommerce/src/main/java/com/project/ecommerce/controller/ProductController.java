package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.ProductValidator;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductValidator productValidator;

    @GetMapping(value = "/showCategory")
    public String showCategory(Model model) {
        // load danh sach category
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        // load danh sach sub-category
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        model.addAttribute("categorys", categoryDtoList);
        model.addAttribute("subCategorys", subCategoryDtoList);
        return "/addProduct";
    }

//    @GetMapping(value = "/showCategory")
//    @ResponseBody
//    public String showCategory(@RequestBody ProductForm productForm, Model model) {
//        // load danh sach category
//        List<CategoryDto> categoryDtoList= productService.getAllCategory();
//        model.addAttribute("category-list", categoryDtoList);
//        return "/addProduct";
//    }
//
//    @GetMapping(value = "/showSubCategory")
//    @ResponseBody
//    public String showSubCategory(@RequestBody ProductForm productForm, Model model) {
//        // load danh sach sub-category
//        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
//        model.addAttribute("sub-category-list", subCategoryDtoList);
//        return "/addProduct";
//    }


    @PostMapping(value = "/addProduct/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm, Model model) {
        model.addAttribute("productForm", productForm);
        return "addProductDetail";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productValidator);
        }
    }

    @PostMapping(value = "/addProduct/addDetail")
    public String addProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                             Model model,
                             BindingResult result,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto userDto = userService.getUserByUserName(((UserDetails) auth.getPrincipal()).getUsername());
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        productService.addProduct(productForm, id);
        return "redirect:/listVendorProduct";
    }

    @GetMapping(value = "/listVendorProduct")
    public String getAllProductByVendorId(Model model, Authentication auth) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto userDto = userService.getUserByUserName(((UserDetails) auth.getPrincipal()).getUsername());
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<ProductForm> productFormList = productService.getAllProductByVendorId(id);
        model.addAttribute("productForms", productFormList);
        return "listVendorProduct";
    }

    @GetMapping(value = "/editProduct")
    public String getEditProduct(@ModelAttribute("productId") Long productId, Model model, Authentication auth) {
        UserDetailsDto userDetailsDto = (UserDetailsDto) auth.getPrincipal();
        List<CategoryForm> categoryForms = getCategory();
        ProductForm productForm = productService.getVendorProduct(productId);
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryForms);
        model.addAttribute("vendorId", userDetailsDto.getUserDto().getId());
        return "editProduct";
    }

    @PostMapping(value = "/editProduct")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              Model model,
                              BindingResult result,
                              final RedirectAttributes redirectAttributes,
                              Authentication auth) {
        UserDto userDto = userService.getUserByUserName(((UserDetails) auth.getPrincipal()).getUsername());
        productService.updateProduct(productForm);
        return "redirect:/listVendorProduct";
    }

    @PostMapping(value = "/deleteProduct")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return null;
    }

    @GetMapping(value = "/showProductsByCategory")
    public String showAllProduct(Model model, @ModelAttribute("categoryId") int categoryId, @ModelAttribute("subCategoryId") int subCategoryId) {
        List<CategoryForm> categoryForms = getCategory();

        List<ProductForm> productFormList = getProduct(categoryId, subCategoryId);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = "/showProducts")
    public String showAllProduct(Model model) {
        List<CategoryForm> categoryForms = getCategory();

        List<ProductForm> productFormList = getProduct(null, null);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    private List<CategoryForm> getCategory() {
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();

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

    private List<ProductForm> getProduct(Integer categoryId, Integer supCategoryId) {
        categoryId = Consts.DEFAULT_VALUE_0.equals(categoryId) ? null : categoryId;
        supCategoryId = Consts.DEFAULT_VALUE_0.equals(supCategoryId) ? null : supCategoryId;
        return productService.getALlProduct(categoryId, supCategoryId);
    }
}
