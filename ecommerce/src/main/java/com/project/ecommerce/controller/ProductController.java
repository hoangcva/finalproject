package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.ProductValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.service.IVendorService;
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
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductValidator productValidator;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping(value = "/vendor/show/category")
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

    @GetMapping(value = "/vendor/product/add/detail")
    public String showDetail(@ModelAttribute("productForm") ProductForm productForm,
                             @ModelAttribute("vendorForm") VendorForm vendorForm,
                             Model model,
                             HttpServletRequest request) {
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
        return "vendor/addProductDetail";
    }

    @GetMapping(value = "/vendor/product/add/detailExtend")
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

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productValidator);
        }
    }

    @PostMapping(value = "/vendor/product/add/detail")
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

    @GetMapping("/vendor/success")
    public String success(Model model,
                          @ModelAttribute("vendorForm") VendorForm vendorForm) {
        return "/vendor/success";
    }

    @GetMapping(value = "/vendor/product/view")
    public String getAllProductByVendorId(Model model,
                                          Authentication auth,
                                          @ModelAttribute("vendorForm") VendorForm vendorForm) {
        Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "vendor/listVendorProduct";
    }

    @GetMapping(value = "/vendor/product/edit")
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

    @PostMapping(value = "/vendor/product/edit")
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

    @PostMapping(value = "/vendor/product/delete")
    public String deleteProduct(@RequestParam Long productId,
                                @ModelAttribute("vendorForm") VendorForm vendorForm) {
        productService.deleteProduct(productId);
        return null;
    }

//    @GetMapping(value = "/product/view/byCategory")
//    public String showAllProduct(Model model, @ModelAttribute("categoryId") int categoryId, @ModelAttribute("subCategoryId") int subCategoryId) {
//        List<CategoryForm> categoryForms = productService.getCategory();
//        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, null);
//        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("categories", categoryForms);
//        return "viewProductList";
//    }

    @GetMapping(value = {"/", "/product/view/list"})
    public String showAllProduct(Model model, Authentication auth) {
        List<CategoryForm> categoryForms = productService.getCategory();
        List<ProductForm> productFormList = productService.getAllProductMainPage(null, null, null);
        model.addAttribute("productFormList", productFormList);
//        List<ProductForm> productFormListTop10 = productService.getTop10NewestProduct(null, null, null);
//        model.addAttribute("productFormListTop10", productFormListTop10);
        model.addAttribute("categories", categoryForms);
        if (auth != null) {
            UserDto userDto = ((UserDetailsDto) auth.getPrincipal()).getUserDto();
            if (Consts.ROLE_VENDOR.equals(userDto.getRole())) {
                Long vendorId = userDto.getId();
                VendorForm vendorForm = vendorService.getInfo(vendorId);
                model.addAttribute("vendorForm", vendorForm);
            }
        }

        return "viewProductList";
    }

    @GetMapping(value = "/product/view/detail")
    public String viewProductDetail(Model model,
                                    @ModelAttribute("productId") Long productId,
                                    @ModelAttribute("vendorId") Long vendorId,
                                    Authentication auth) {
        ProductForm productForm = productService.getProductDetail(productId, vendorId);
        if (auth != null) {
            Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            boolean isLiked = customerService.isLiked(productId, vendorId, customerId);
            productForm.setLiked(isLiked);
        }
        List<VendorProductForm> vendorList = productService.getVendorListByProduct(productId);
        VendorForm vendorForm = vendorService.getInfo(vendorId);
        model.addAttribute("vendorId", vendorId);
        model.addAttribute("productForm", productForm);
        model.addAttribute("vendorList", vendorList);
        model.addAttribute("vendorForm", vendorForm);
        return "viewProductDetail";
    }

//    @GetMapping(value = "/vendor/product/add/search")
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
//            return "redirect:/vendor/show/category";
//        }
//
//        redirectAttributes.addFlashAttribute("response", "notEmpty");
//        redirectAttributes.addFlashAttribute("productFormList", productFormList);
//        return "redirect:/vendor/show/category";
//
////        model.addAttribute("response", "notEmpty");
////        model.addAttribute("productFormList", productFormList);
////        return"vendor/addProduct";
//    }

    @GetMapping(value = "/vendor/product/add/search")
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

    @GetMapping(value = "/vendor/product/view/search")
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

    @GetMapping(value = "/product/view/list/search")
    public String searchProduct(HttpServletRequest request, Model model,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                final RedirectAttributes redirectAttributes,
                                ModelMap modelMap,
                                Authentication auth) {
        List<ProductForm> productFormList = productService.getAllProductMainPage(null, null, keyword);

        if (productFormList.isEmpty()) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-product-grid";
    }


    @GetMapping(value = "/product/view/byCategory")
    public String showAllProduct(HttpServletRequest request, Model model,
                                 @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                 @RequestParam(value = "subCategoryId", required = false) Integer subCategoryId,
                                 ModelMap modelMap) {
//        List<CategoryForm> categoryForms = productService.getCategory();
        List<ProductForm> productFormList = productService.getAllProductMainPage(categoryId, subCategoryId, null);
        // TODO
        if (productFormList.isEmpty()) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-product-grid";
    }

    @PostMapping(value = "/vendor/product/activate")
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

    @PostMapping(value = "/admin/product/activate")
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

    @GetMapping(value = "/admin/product/view/list/search")
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

    @GetMapping(value = "/admin/product/view/list")
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

    @GetMapping(value = "/customer/vendor-product/view")
    public String getAllProductByVendorIdForCustomer(Model model,
                                                     Authentication auth,
                                                     @ModelAttribute("vendorId") Long vendorId) {
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "customer/listVendorProduct";
    }

    @GetMapping(value = "/admin/vendor-product/view")
    public String getAllProductByVendorIdForAdmin(Model model,
                                                  Authentication auth,
                                                  @ModelAttribute("vendorId") Long vendorId) {
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "admin/listVendorProduct";
    }

    @GetMapping(value = "/customer/vendor-product/view/search")
    public String searchProductVendorForCustomer(HttpServletRequest request, Model model,
                                                 @RequestParam(value = "keyword", required = false) String keyword,
                                                 @RequestParam(value = "vendorId", required = false) Long vendorId,
                                                 final RedirectAttributes redirectAttributes,
                                                 ModelMap modelMap,
                                                 @ModelAttribute("vendorForm") VendorForm vendorForm) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword, null, vendorId);
        if (productFormList.isEmpty()) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-vendor-product-for-customer";
    }
}
