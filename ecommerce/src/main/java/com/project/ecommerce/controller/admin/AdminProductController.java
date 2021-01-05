package com.project.ecommerce.controller.admin;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.AdminProductValidator;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.CountriesDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/product")
public class AdminProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private AdminProductValidator adminProductValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(adminProductValidator);
        }
    }

    @GetMapping("/success")
    public String success() {
        return "/admin/success";
    }

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

    @GetMapping(value = "/category")
    public String showCategory(Model model) {
        // load danh sach category
        List<CategoryDto> categoryDtoList = productService.getAllCategory();
        // load danh sach sub-category
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
//        session.setAttribute("categoryDtoList", categoryDtoList);
//        session.setAttribute("subCategoryDtoList", subCategoryDtoList);
        return "admin/addProduct";
    }

    @GetMapping(value = "/add/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm,
                             Model model,
                             Authentication auth) {
        CategoryDto categoryDto = productService.findCategory(productForm.getCategoryId());
        SubCategoryDto subCategoryDto = productService.findSubCategory(productForm.getSubCategoryId());

        String categoryName = categoryDto.getName();
        String subCategoryName = subCategoryDto.getName();

        List<CountriesDto> countriesDtoList = productService.getCountries();

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);
        model.addAttribute("productForm", productForm);
        model.addAttribute("countriesDtoList", countriesDtoList);
        return "admin/addProductDetail";
    }

    @PostMapping(value = "/add/detail")
    public String addProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                             BindingResult bindingResult,
                             Model model,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth) {
        productForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<CountriesDto> countriesDtoList = productService.getCountries();
            model.addAttribute("countriesDtoList", countriesDtoList);
            model.addAttribute("categoryName", productForm.getCategoryName());
            model.addAttribute("subCategoryName", productForm.getSubCategoryName());
            return "admin/addProductDetail";
        }
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        Message result = productService.addProduct(productForm, id);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin/success";
    }

    @GetMapping(value = "/edit")
    public String getEditProduct(@ModelAttribute("productId") Long productId,
                                 @ModelAttribute("vendorId") Long vendorId,
                                 Model model) {
        List<CategoryDto> categoryDtoList = productService.getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = productService.getVendorProduct(productId, vendorId);

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

        List<CountriesDto> countriesDtoList = productService.getCountries();
        model.addAttribute("productForm", productForm);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("countriesDtoList", countriesDtoList);
        model.addAttribute("subCategories", subCategoryDtoList);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);

        return "admin/editProduct";
    }

    @PostMapping(value = "/edit")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              BindingResult bindingResult,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        productForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<ProductImageForm> productImageFormList = productService.getProductImage(productForm.getProductId());
            productForm.setProductImageFormList(productImageFormList);
            List<CategoryDto> categoryDtoList = productService.getAllCategory();
            List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
            List<CountriesDto> countriesDtoList = productService.getCountries();
            model.addAttribute("countriesDtoList", countriesDtoList);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("subCategories", subCategoryDtoList);
            model.addAttribute("categoryName", productForm.getCategoryName());
            model.addAttribute("subCategoryName", productForm.getSubCategoryName());
            return "admin/editProduct";
        }
        Message result = productService.updateProductAdmin(productForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin/product/view/list";
    }
}
