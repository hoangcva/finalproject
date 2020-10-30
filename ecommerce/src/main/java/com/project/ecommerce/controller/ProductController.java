package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;

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

    @PostMapping(value = "/addProduct/addDetail")
    public String addProduct(@ModelAttribute("productForm") ProductForm productForm, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserDto userDto = userService.getUserByUserName(((UserDetails) auth.getPrincipal()).getUsername());
        productService.addProduct(productForm, userDto.getId());
        return null;
    }
}
