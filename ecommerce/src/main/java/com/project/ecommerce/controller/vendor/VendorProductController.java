package com.project.ecommerce.controller.vendor;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.VendorProductValidator;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.CountriesDto;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.ProductImageForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.form.VendorProductForm;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendor/product")
public class VendorProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private VendorProductValidator vendorProductValidator;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(vendorProductValidator);
        }
    }

    @GetMapping(value = "/category")
    public String showCategory(Model model, @ModelAttribute("vendorForm") VendorForm vendorForm) {
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
        return "vendor/addProduct";
    }

    @GetMapping(value = "/add/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm,
                             @ModelAttribute("vendorForm") VendorForm vendorForm,
                             Model model,
                             HttpServletRequest request,
                             Authentication auth) {
//        List<CategoryDto> categoryDtoList = (List<CategoryDto>) request.getSession().getAttribute("categoryDtoList");
//        List<SubCategoryDto> subCategoryDtoList = (List<SubCategoryDto>) request.getSession().getAttribute("subCategoryDtoList");

//        String categoryName = categoryDtoList.stream()
//                .filter(category -> productForm.getCategoryId().equals(category.getId()))
//                .findAny()
//                .map(category -> category.getName())
//                .orElse("");
//        String subCategoryName = subCategoryDtoList.stream()
//                .filter(subCategory -> productForm.getSubCategoryId().equals(subCategory.getId()))
//                .findAny()
//                .map(subCategory -> subCategory.getName())
//                .orElse("");

        CategoryDto categoryDto = productService.findCategory(productForm.getCategoryId());
        SubCategoryDto subCategoryDto = productService.findSubCategory(productForm.getSubCategoryId());

        String categoryName = categoryDto.getName();
        String subCategoryName = subCategoryDto.getName();

        List<CountriesDto> countriesDtoList = productService.getCountries();

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);
        model.addAttribute("productForm", productForm);
        model.addAttribute("countriesDtoList", countriesDtoList);
        if (Consts.ROLE_ADMIN.equals(((UserDetailsDto) auth.getPrincipal()).getUserDto().getRole())) {
            return "admin/addProductDetail";
        }
        return "vendor/addProductDetail";
    }

    @GetMapping(value = "/add/detailExtend")
    public String showDetail(@ModelAttribute("productId") Long productId,
                             @ModelAttribute("vendorId") Long vendorId,
                             @ModelAttribute("vendorForm") VendorForm vendorForm,
                             Model model,
                             HttpServletRequest request) {
        ProductForm productForm = productService.getProductDetailExtend(productId);

        CategoryDto categoryDto = productService.findCategory(productForm.getCategoryId());
        SubCategoryDto subCategoryDto = productService.findSubCategory(productForm.getSubCategoryId());

        String categoryName = categoryDto.getName();
        String subCategoryName = subCategoryDto.getName();
        List<CountriesDto> countriesDtoList = productService.getCountries();
        productForm.setCategoryName(categoryName);
        productForm.setSubCategoryName(subCategoryName);
        productForm.setAction(Consts.ACTION_ADDEXTEND);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("subCategoryName", subCategoryName);
        model.addAttribute("productForm", productForm);
        model.addAttribute("countriesDtoList", countriesDtoList);
        return "vendor/addProductDetailExtend";
    }

    @PostMapping(value = "/add/detail")
    public String addProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                             BindingResult bindingResult,
                             Model model,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth,
                             HttpServletRequest request,
                             @ModelAttribute("vendorForm") VendorForm vendorForm) {
        productForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<CountriesDto> countriesDtoList = productService.getCountries();
            model.addAttribute("countriesDtoList", countriesDtoList);
            model.addAttribute("categoryName", productForm.getCategoryName());
            model.addAttribute("subCategoryName", productForm.getSubCategoryName());
            return "vendor/addProductDetail";
        }
        Message result = new Message("", true);
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        if (Consts.ACTION_ADDEXTEND.equals(productForm.getAction())) {
            result = productService.addProductExtend(productForm, id);
        } else {
            result = productService.addProduct(productForm, id);
        }
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/vendor/success";
    }

    @GetMapping(value = "/view")
    public String getAllProductByVendorId(Model model,
                                          Authentication auth,
                                          @ModelAttribute("vendorForm") VendorForm vendorForm) {
        Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "vendor/listVendorProduct";
    }

    @GetMapping(value = "/edit")
    public String getEditProduct(@ModelAttribute("productId") Long productId,
                                 @ModelAttribute("vendorId") Long vendorId,
                                 @ModelAttribute("vendorForm") VendorForm vendorForm,
                                 Model model, Authentication auth) {
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
        if (productForm.getCreatedBy() != vendorId) {
            productForm.setAction(Consts.ACTION_UPDATEEXTEND);
            return "vendor/editProductExtend";
        }
        return "vendor/editProduct";
    }

    @PostMapping(value = "/edit")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              BindingResult bindingResult,
                              Model model,
                              final RedirectAttributes redirectAttributes,
                              Authentication auth,
                              @ModelAttribute("vendorForm") VendorForm vendorForm) {
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
            return "vendor/editProduct";
        }
        Message result = productService.updateProduct(productForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/vendor/product/view";
    }

    @PostMapping(value = "/delete")
    public String deleteProduct(@RequestParam Long productId,
                                @ModelAttribute("vendorForm") VendorForm vendorForm) {
        productService.deleteProduct(productId);
        return null;
    }

    //    @GetMapping(value = "/product/add/search")
//    public String showAllProductByKeyWord(Model model,
//                                          @RequestParam(value = "keyword", required = false) String keyword,
//                                          final RedirectAttributes redirectAttributes
////            , @Param("keyword") String keyword
//    ) {
//        List<ProductForm> productFormList = productService.getProducts(null, null, keyword);
////        model.addAttribute("keyword", keyword);
//        if (productFormList == null) {
////            model.addAttribute("response", "empty");
//            redirectAttributes.addFlashAttribute("response", "empty");
////            return "vendor/addProduct";
//            return "redirect:/vendor/product/category";
//        }
//
//        redirectAttributes.addFlashAttribute("response", "notEmpty");
//        redirectAttributes.addFlashAttribute("productFormList", productFormList);
//        return "redirect:/vendor/product/category";
//
////        model.addAttribute("response", "notEmpty");
////        model.addAttribute("productFormList", productFormList);
////        return"vendor/addProduct";
//    }

    @GetMapping(value = "/add/search")
    public String showAllProductByKeyWord(HttpServletRequest request, Model model,
                                          @RequestParam(value = "keyword", required = false) String keyword,
                                          final RedirectAttributes redirectAttributes,
                                          ModelMap modelMap,
                                          Authentication auth,
                                          @ModelAttribute("vendorForm") VendorForm vendorForm) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword, null, null);
        List<ProductForm> tempList = new ArrayList<>();

        if (productFormList.size() > 0) {
            Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();

            for (ProductForm productForm : productFormList) {
                if (!vendorId.equals(productForm.getVendorId())) {
                    tempList.add(productForm);
                }
            }
        }

        if (tempList.size() == 0) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", tempList);
        return "/fragments/template :: table-product-search";
    }

    @GetMapping(value = "/view/search")
    public String searchProductVendor(HttpServletRequest request, Model model,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      final RedirectAttributes redirectAttributes,
                                      ModelMap modelMap,
                                      Authentication auth,
                                      @ModelAttribute("vendorForm") VendorForm vendorForm) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword, null, null);
        List<ProductForm> tempList = new ArrayList<>();
        if (!productFormList.isEmpty()) {
            Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            for (ProductForm productForm : productFormList) {
                if (vendorId.equals(productForm.getVendorId())) {
                    tempList.add(productForm);
                }
            }
        }
        if (tempList.isEmpty()) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", tempList);
        return "/fragments/template :: table-vendor-product";
    }

    @PostMapping(value = "/activate")
    public String activateProductVendor(Model model, @RequestBody VendorProductForm vendorProductForm, Authentication auth) {
        Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        String role = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getRole();
        vendorProductForm.setVendorId(vendorId);
        Message result = productService.activateProduct(vendorProductForm);
        if (result.isSuccess() == false) {
            model.addAttribute("message", result.getMessage());
            model.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }
        List<ProductForm> productFormList = productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
        model.addAttribute("productFormList", productFormList);
        if (Consts.ROLE_VENDOR.equals(role)) {
            return "/fragments/template :: table-vendor-product";
        } else {
            return "/fragments/template :: table-product-admin";
        }
    }
}
