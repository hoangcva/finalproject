package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.VendorProductValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.*;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
    private VendorProductValidator vendorProductValidator;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private ITransporterService transporterService;

//    @GetMapping(value = "/product/view/byCategory")
//    public String showAllProduct(Model model, @ModelAttribute("categoryId") int categoryId, @ModelAttribute("subCategoryId") int subCategoryId) {
//        List<CategoryForm> categoryForms = productService.getCategory();
//        List<ProductForm> productFormList = productService.getProducts(categoryId, subCategoryId, null);
//        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("categories", categoryForms);
//        return "viewProductList";
//    }

    @GetMapping(value = {"/", "/product/view/list"})
    public String showAllProduct(Model model, Authentication auth, HttpSession session) {
        List<CategoryForm> categoryForms = productService.getCategory();
        List<ProductForm> productFormList = productService.getAllProductMainPage(null, null, null);
        model.addAttribute("productFormList", productFormList);
        List<ProductForm> productFormListTop10Newest = productService.getTop10NewestProduct(null, null, null);
        model.addAttribute("productFormListTop10Newest", productFormListTop10Newest);
        List<ProductForm> productFormListTop10BestSeller = productService.getTop12BestSeller(null, null, null);
        model.addAttribute("productFormListTop10BestSeller", productFormListTop10BestSeller);
        model.addAttribute("categories", categoryForms);
        if (auth != null) {
            UserDto userDto = ((UserDetailsDto) auth.getPrincipal()).getUserDto();
            if (Consts.ROLE_VENDOR.equals(userDto.getRole())) {
                Long vendorId = userDto.getId();
                VendorForm vendorForm = vendorService.getInfo(vendorId);
                model.addAttribute("vendorForm", vendorForm);
            } else if (Consts.ROLE_USER.equals(userDto.getRole())) {
                Long customerId = userDto.getId();
                int quantityTotal = cartService.getCart(customerId).getQuantityTotal();
                session.setAttribute("quantityTotal", quantityTotal);
            } else if (Consts.ROLE_ADMIN.equals(userDto.getRole())) {
                Long countProgressingOrder = adminService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_PROGRESSING, null, null);
                session.setAttribute("countProgressingOrder", countProgressingOrder);
            } else if (Consts.ROLE_SHIPPER.equals(userDto.getRole())) {
                Long transporterId = userDto.getId();
                Long countReadyOrder = transporterService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_READY, transporterId, null);
                session.setAttribute("countReadyOrder", countReadyOrder);
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
}
