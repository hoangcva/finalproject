package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CustomerAddressDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IOrderCustomerService;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/customer/order")
public class OrderCustomerController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private ICustomerAddressService addressService;
    @Autowired
    private IOrderCustomerService orderCustomerService;
    @Autowired
    private ITransporterService transporterService;

    @RequestMapping()
    public String init(Model model, Authentication auth) {
        OrderForm orderForm = new OrderForm();
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        Long customerId = userDetails.getUserDto().getId();

        CartInfoForm cartInfoForm = cartService.getCart(customerId);
        orderForm.setCartInfoForm(cartInfoForm);
        CustomerAddressDto customerAddressDto = addressService.getDefault(customerId);

        List<TransporterForm> transporterFormList = transporterService.getTransporterList();

        model.addAttribute("orderForm", orderForm);
        model.addAttribute("defaultAddress", customerAddressDto);
        model.addAttribute("transporterFormList", transporterFormList);
        return "customer/order/orderPage";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("orderForm") OrderForm orderForm,
                              Authentication auth,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        Message result = orderCustomerService.createOrder(orderForm, auth);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/customer/order/success";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "/customer/order/success";
    }

    @GetMapping("/history")
    public String viewOrderHistory(Model model, Authentication auth){
        List<OrderForm> orderFormList = orderCustomerService.getOrderListCustomer(auth);
        model.addAttribute("orderFormList", orderFormList);
        return "/customer/order/history";
    }

    @GetMapping("/detail")
    public String viewOrderDetail(@ModelAttribute("orderId") Long orderId, Model model) {
        OrderForm orderForm = orderCustomerService.getOrderDetailCustomer(orderId);
        model.addAttribute("orderForm", orderForm);
        return "/customer/order/detail";
    }

    @GetMapping("/cancel")
    public String cancelOrder(@RequestParam("id") Long id,
                              @RequestParam("orderDspId") String orderDspId,
                              @RequestParam("orderStatus") String orderStatus,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.setOrderDspId(orderDspId);
        Message result = orderCustomerService.cancelOrder(orderForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/customer/order/history";
    }

    @GetMapping("/success")
    public String finishOrder(@RequestParam("id") Long id,
                              @RequestParam("orderDspId") String orderDspId,
                              @RequestParam("orderStatus") String orderStatus,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.setOrderDspId(orderDspId);
        orderForm.setOrderStatus(orderStatus);
        Message result = orderCustomerService.finishOrder(orderForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/customer/order/history";
    }
}
