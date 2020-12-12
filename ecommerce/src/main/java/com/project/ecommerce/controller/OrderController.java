package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.CartLineInfoForm;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private ICustomerAddressService addressService;

    @RequestMapping("/customer/order")
    public String init(Model model, Authentication auth) {
        OrderForm orderForm = new OrderForm();
        CartInfoForm cartInfoForm = new CartInfoForm();
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        cartInfoForm = cartService.getCart(userDetails.getUserDto().getId());
        orderForm.setCartInfoForm(cartInfoForm);
        model.addAttribute("orderForm", orderForm);
        return "customer/order/orderPage";
    }
}
