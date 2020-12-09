package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.ProductValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CategoryForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorProductForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String showCategory(Model model, HttpSession session) {
        // load danh sach category
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        // load danh sach sub-category
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
        session.setAttribute("categoryDtoList", categoryDtoList);
        session.setAttribute("subCategoryDtoList", subCategoryDtoList);
        return "vendor/addProduct";
    }

//    @GetMapping(value = "/vendor/getCategory")
//    @ResponseBody
//    public ResponseEntity<?> showCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
//        // load danh sach category
//        List<CategoryDto> categoryDtoList= productService.getAllCategory();
//        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK) ;
//    }
//
//    @GetMapping(value = "/vendor/getSubCategory")
//    @ResponseBody
//    public ResponseEntity<?> showSubCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
//        // load danh sach sub-category
//        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
//        return new ResponseEntity<>(subCategoryDtoList, HttpStatus.OK);
//    }


    @PostMapping(value = "/vendor/addProduct/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm, Model model, HttpServletRequest request) {
        List<CategoryDto> categoryDtoList = (List<CategoryDto>) request.getSession().getAttribute("categoryDtoList");
        List<SubCategoryDto> subCategoryDtoList = (List<SubCategoryDto>) request.getSession().getAttribute("subCategoryDtoList");

        String categoryName = categoryDtoList.stream()
                .filter(category -> productForm.getCategoryId().equals(category.getId()))
                .findAny()
                .map(category -> category.getName())
                .orElse("");
        String subCategoryName = subCategoryDtoList.stream()
                .filter(subCategory -> productForm.getSubCategoryId().equals(subCategory.getId()))
                .findAny()
                .map(subCategory -> subCategory.getName())
                .orElse("");

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);
        model.addAttribute("productForm", productForm);
        return "vendor/addProductDetail";
    }

    @GetMapping(value = "/vendor/addProduct/detailExtend")
    public String showDetail(@ModelAttribute("productId") Long productId,
                             @ModelAttribute("vendorId") Long vendorId,
                             Model model,
                             HttpServletRequest request) {
        ProductForm productForm = productService.getProductDetail(productId, vendorId);
        List<CategoryDto> categoryDtoList = (List<CategoryDto>) request.getSession().getAttribute("categoryDtoList");
        List<SubCategoryDto> subCategoryDtoList = (List<SubCategoryDto>) request.getSession().getAttribute("subCategoryDtoList");

        String categoryName = categoryDtoList.stream()
                .filter(category -> productForm.getCategoryId().equals(category.getId()))
                .findAny()
                .map(category -> category.getName())
                .orElse("");
        String subCategoryName = subCategoryDtoList.stream()
                .filter(subCategory -> productForm.getSubCategoryId().equals(subCategory.getId()))
                .findAny()
                .map(subCategory -> subCategory.getName())
                .orElse("");

        productForm.setCategoryName(categoryName);
        productForm.setSubCategoryName(subCategoryName);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);
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
                             BindingResult result,
                             Model model,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth,
                             HttpServletRequest request) {
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        if (result.hasErrors()) {
            model.addAttribute("categoryName", productForm.getCategoryName());
            model.addAttribute("subCategoryName", productForm.getSubCategoryName());
            return "vendor/addProductDetail";
        }
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
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = productService.getVendorProduct(productId);
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
        return "vendor/editProduct";
    }

    @PostMapping(value = "/vendor/editProduct")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              BindingResult result,
                              Model model,
                              final RedirectAttributes redirectAttributes,
                              Authentication auth) {
        if (result.hasErrors()) {
            List<CategoryDto> categoryDtoList= productService.getAllCategory();
            List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("subCategories", subCategoryDtoList);
            return "vendor/editProduct";
        }
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

        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, null);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = {"/", "/showProducts"})
    public String showAllProduct(Model model) {
        List<CategoryForm> categoryForms = productService.getCategory();

        List<ProductForm> productFormList = productService.getProducts(null, null, null);
        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = "viewProductDetail")
    public String viewProductDetail(Model model, @ModelAttribute("productId") Long productId, @ModelAttribute("vendorId") Long vendorId ) {
        ProductForm productForm = productService.getProductDetail(productId, vendorId);
        List<VendorProductForm> vendorList = productService.getVendorListByProduct(productId);
        model.addAttribute("vendorId", vendorId);
        model.addAttribute("productForm", productForm);
        model.addAttribute("vendorList", vendorList);
        return "viewProductDetail";
    }

    @GetMapping(value = "/vendor/addProduct/search")
    public String showAllProductByKeyWord(Model model,
                                          @RequestParam(value = "keyword", required = false) String keyword,
                                          final RedirectAttributes redirectAttributes
//            , @Param("keyword") String keyword
    ) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword);
//        model.addAttribute("keyword", keyword);
        if (productFormList == null) {
//            model.addAttribute("response", "empty");
            redirectAttributes.addFlashAttribute("response", "empty");
//            return "vendor/addProduct";
            return "redirect:/vendor/showCategory";
        }

        redirectAttributes.addFlashAttribute("response", "notEmpty");
        redirectAttributes.addFlashAttribute("productFormList", productFormList);
//        model.addAttribute("response", "notEmpty");
//        model.addAttribute("productFormList", productFormList);
//        return"vendor/addProduct";
        return "redirect:/vendor/showCategory";
    }
}
