package com.project.ecommerce.controller.customer;

import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/customer/product")
public class CustomerProductController {
    @Autowired
    private IProductService productService;

    @GetMapping(value = "/vendor/view")
    public String getAllProductByVendorIdForCustomer(Model model,
                                                     Authentication auth,
                                                     @ModelAttribute("vendorId") Long vendorId) {
        List<ProductForm> productFormList = productService.getAllProductByVendorId(vendorId);
        model.addAttribute("productFormList", productFormList);
//        model.addAttribute("vendorId", vendorId);
        return "customer/listVendorProduct";
    }

    @GetMapping(value = "/vendor/view/search")
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
