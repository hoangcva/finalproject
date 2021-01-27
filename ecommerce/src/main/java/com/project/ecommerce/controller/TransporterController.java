package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dto.UserDetailsDto;
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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/transporter")
public class TransporterController {
    @Autowired
    private ITransporterService transporterService;

    @GetMapping("/orders")
    public String viewOrderHistory(Model model, Authentication auth, HttpSession session){
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<OrderForm> orderFormList = transporterService.getOrderList(Consts.ORDER_STATUS_DELIVERING, id);
        model.addAttribute("orderFormList", orderFormList);
        model.addAttribute("orderForm", new OrderForm());
        Long countReadyOrder = transporterService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_READY, id, null);
        model.addAttribute("countReadyOrder", countReadyOrder);
        session.setAttribute("countReadyOrder", countReadyOrder);
        model.addAttribute("countOrder", orderFormList.size());
        return "/transporter/orders";
    }

    @PostMapping("/order/update")
    public String updateOrderStatus(@ModelAttribute("orderForm") OrderForm orderForm,
                                    Model model,
                                    final RedirectAttributes redirectAttributes,
                                    HttpSession session,
                                    Authentication auth) {
        Long id = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<OrderForm> orderFormList = transporterService.getOrderList(Consts.ORDER_STATUS_DELIVERING, id);
        Message result = transporterService.updateOrderStatus(orderForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        Long countReadyOrder = transporterService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_READY, id, null);
        model.addAttribute("countReadyOrder", countReadyOrder);
        session.setAttribute("countReadyOrder", countReadyOrder);
        model.addAttribute("countOrder", orderFormList.size());
        return "redirect:/transporter/orders";
    }
}
