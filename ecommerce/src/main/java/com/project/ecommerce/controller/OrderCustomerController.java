package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CustomerAddressDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IOrderCustomerService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderCustomerController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private ICustomerAddressService addressService;
    @Autowired
    private IOrderCustomerService orderCustomerService;

    @RequestMapping("/customer/order")
    public String init(Model model, Authentication auth) {
        OrderForm orderForm = new OrderForm();
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        Long customerId = userDetails.getUserDto().getId();

        CartInfoForm cartInfoForm = cartService.getCart(customerId);
        orderForm.setCartInfoForm(cartInfoForm);
        CustomerAddressDto customerAddressDto = addressService.getDefault(customerId);

        model.addAttribute("orderForm", orderForm);
        model.addAttribute("defaultAddress", customerAddressDto);
        return "customer/order/orderPage";
    }

    @PostMapping("/customer/order/create")
    public String createOrder(@ModelAttribute("orderForm") OrderForm orderForm,
                              Authentication auth,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        Message result = orderCustomerService.createOrder(orderForm, auth);
//        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("message", result.getMessage());
            redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
            return "redirect:/customer/order/success";
//        } else {
//            redirectAttributes.addFlashAttribute("message", result.getMessage());
//            redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
//            return "redirect:/customer/order/unsuccessful";
//        }
    }

    @GetMapping("/customer/order/success")
    public String success(Model model) {
        return "/customer/order/success";
    }

//    @GetMapping("/customer/order/unsuccessful")
//    public String unsuccessful(Model model) {
//        return "/customer/order/unsuccessful";
//    }

    @GetMapping("/customer/order/history")
    public String viewOrderHistory(Model model, Authentication auth){
        List<OrderForm> orderFormList = orderCustomerService.getOrderListCustomer(auth);
        model.addAttribute("orderFormList", orderFormList);
        return "/customer/order/history";
    }
}
