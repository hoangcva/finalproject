package com.project.ecommerce.controller;

import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transporter")
public class TransporterController {
    @Autowired
    private ITransporterService transporterService;

    @GetMapping("/orders")
    public String viewOrderHistory(Model model, Authentication auth){
        List<OrderForm> orderFormList = transporterService.getOrderList(null);
        model.addAttribute("orderFormList", orderFormList);
        model.addAttribute("orderForm", new OrderForm());
        return "/admin/order/history";
    }

    @PostMapping("/order/update")
    public String updateOrderStatus(@ModelAttribute("orderForm") OrderForm orderForm,
                                    Model model,
                                    final RedirectAttributes redirectAttributes) {
        Message result = transporterService.updateOrderStatus(orderForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin/orders";
    }
}
