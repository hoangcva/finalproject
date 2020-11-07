package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.ProductValidator;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductValidator productValidator;

    @GetMapping(value = "/vendor/showCategory")
    public String showCategory(Model model) {
        // load danh sach category
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        // load danh sach sub-category
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
        return "vendor/addProduct";
    }

    @GetMapping(value = "/vendor/getCategory")
    @ResponseBody
    public ResponseEntity<?> showCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
        // load danh sach category
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK) ;
    }

    @GetMapping(value = "/vendor/getSubCategory")
    @ResponseBody
    public ResponseEntity<?> showSubCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
        // load danh sach sub-category
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        return new ResponseEntity<>(subCategoryDtoList, HttpStatus.OK);
    }


    @PostMapping(value = "/vendor/addProduct/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm, Model model) {
        model.addAttribute("productForm", productForm);
        return "vendor/addProductDetail";
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

    @PostMapping(value = "/vendor/addProduct/addDetail")
    public String addProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                             Model model,
                             BindingResult result,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth) {
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        productService.addProduct(productForm, id);
        return "redirect:/vendor/listVendorProduct";
    }

    @GetMapping(value = "/vendor/listVendorProduct")
    public String getAllProductByVendorId(Model model, Authentication auth) {
        Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productForms", productFormList);
        return "vendor/listVendorProduct";
    }

    @GetMapping(value = "/vendor/editProduct")
    public String getEditProduct(@ModelAttribute("productId") Long productId, Model model, Authentication auth) {
        UserDetailsDto userDetailsDto = (UserDetailsDto) auth.getPrincipal();
        List<CategoryForm> categoryForms = productService.getCategory();
        ProductForm productForm = productService.getVendorProduct(productId);
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryForms);
        model.addAttribute("vendorId", userDetailsDto.getUserDto().getId());
        return "vendor/editProduct";
    }

    @PostMapping(value = "/vendor/editProduct")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              Model model,
                              BindingResult result,
                              final RedirectAttributes redirectAttributes,
                              Authentication auth) {
        UserDto userDto = userService.getUserByUserName(((UserDetails) auth.getPrincipal()).getUsername());
        productService.updateProduct(productForm);
        return "redirect:/vendor/listVendorProduct";
    }

    @PostMapping(value = "/vendor/deleteProduct")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return null;
    }

    @GetMapping(value = "/showProductsByCategory")
    public String showAllProduct(Model model, @ModelAttribute("categoryId") int categoryId, @ModelAttribute("subCategoryId") int subCategoryId) {
        List<CategoryForm> categoryForms = productService.getCategory();

        List<ProductForm> productFormList = productService.getProduct(categoryId, subCategoryId);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = {"/", "/showProducts"})
    public String showAllProduct(Model model) {
        List<CategoryForm> categoryForms = productService.getCategory();

        List<ProductForm> productFormList = productService.getProduct(null, null);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = "viewProductDetail")
    public String viewProductDetail(Model model) {
        return "viewProductDetail";
    }
}
