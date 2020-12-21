package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.ProductValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping(value = "/vendor/show/category")
    public String showCategory(Model model, @ModelAttribute("vendorForm") VendorForm vendorForm) {
        // load danh sach category
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
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
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productValidator);
        }
    }

    @PostMapping(value = "/vendor/product/add/detail")
    public String addProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                             @ModelAttribute("vendorForm") VendorForm vendorForm,
                             BindingResult bindingResult,
                             Model model,
                             final RedirectAttributes redirectAttributes,
                             Authentication auth,
                             HttpServletRequest request) {
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        productForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<CountriesDto> countriesDtoList = productService.getCountries();
            model.addAttribute("countriesDtoList", countriesDtoList);
            model.addAttribute("categoryName", productForm.getCategoryName());
            model.addAttribute("subCategoryName", productForm.getSubCategoryName());
            return "vendor/addProductDetail";
        }
        Message result = new Message("", true);
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
        return "vendor/listVendorProduct";
    }

    @GetMapping(value = "/vendor/product/edit")
    public String getEditProduct(@ModelAttribute("productId") Long productId,
                                 @ModelAttribute("vendorForm") VendorForm vendorForm,
                                 Model model, Authentication auth) {
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
        ProductForm productForm = productService.getVendorProduct(productId);

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
        return "vendor/editProduct";
    }

    @PostMapping(value = "/vendor/product/edit")
    public String editProduct(@ModelAttribute("productForm") @Validated ProductForm productForm,
                              @ModelAttribute("vendorForm") VendorForm vendorForm,
                              BindingResult result,
                              Model model,
                              final RedirectAttributes redirectAttributes,
                              Authentication auth) {
        if (result.hasErrors()) {
            List<CategoryDto> categoryDtoList= productService.getAllCategory();
            List<SubCategoryDto> subCategoryDtoList = productService.getALLSubCategory();
            List<CountriesDto> countriesDtoList = productService.getCountries();
            model.addAttribute("countriesDtoList", countriesDtoList);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("subCategories", subCategoryDtoList);
            return "vendor/editProduct";
        }
        productService.updateProduct(productForm);
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
    public String showAllProduct(Model model) {
        List<CategoryForm> categoryForms = productService.getCategory();

        List<ProductForm> productFormList = productService.getProducts(null, null, null);

        model.addAttribute("productFormList", productFormList);
        model.addAttribute("categories", categoryForms);
        return "viewProductList";
    }

    @GetMapping(value = "/product/view/detail")
    public String viewProductDetail(Model model,
                                    @ModelAttribute("productId") Long productId,
                                    @ModelAttribute("vendorId") Long vendorId) {
        ProductForm productForm = productService.getProductDetail(productId, vendorId);
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
    public String showAllProductByKeyWord(HttpServletRequest request,Model model,
                                          @RequestParam(value = "keyword", required = false) String keyword,
                                          final RedirectAttributes redirectAttributes,
                                          ModelMap modelMap,
                                          Authentication auth,
                                          @ModelAttribute("vendorForm") VendorForm vendorForm) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword);
        List<ProductForm> tempList = new ArrayList<>();

        if (productFormList.size() > 0) {
            Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();

            for (ProductForm productForm : productFormList) {
                if (!vendorId.equals(productForm.getVendorId())) {
                    tempList.add(productForm);
                }
            }
        }

        if (productFormList.size() == 0) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", tempList);
        return "/fragments/template :: table-product";
    }

    @GetMapping(value = "/vendor/product/view/search")
    public String searchProductVendor(HttpServletRequest request,Model model,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      final RedirectAttributes redirectAttributes,
                                      ModelMap modelMap,
                                      Authentication auth,
                                      @ModelAttribute("vendorForm") VendorForm vendorForm) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword);
        List<ProductForm> tempList = new ArrayList<>();
        if (productFormList.size() > 0) {
            Long vendorId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            for (ProductForm productForm : productFormList) {
                if (vendorId.equals(productForm.getVendorId())) {
                    tempList.add(productForm);
                }
            }
        }
        if (productFormList.size() == 0) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", tempList);
        return "/fragments/template :: table-product-grid";
    }

    @GetMapping(value = "/product/view/list/search")
    public String searchProduct(HttpServletRequest request,Model model,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      final RedirectAttributes redirectAttributes,
                                      ModelMap modelMap,
                                      Authentication auth) {
        List<ProductForm> productFormList = productService.getProducts(null, null, keyword);

        if (productFormList.size() == 0) {
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
                                 ModelMap modelMap
    ) {
        List<CategoryForm> categoryForms = productService.getCategory();
        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, null);

        if (productFormList.size() == 0) {
            Message result = new Message("Product not found!", false);
            modelMap.addAttribute("message", result.getMessage());
            modelMap.addAttribute("isSuccess", result.isSuccess());
            return "/fragments/template :: display-error-message";
        }

        modelMap.addAttribute("productFormList", productFormList);
        return "/fragments/template :: table-product-grid";
    }
}
