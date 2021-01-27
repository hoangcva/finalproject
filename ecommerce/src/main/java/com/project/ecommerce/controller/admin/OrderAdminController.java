package com.project.ecommerce.controller.admin;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.service.IAdminService;
import com.project.ecommerce.service.IOrderAdminService;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.util.Message;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrderAdminController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IOrderAdminService orderAdminService;
    @Autowired
    private ITransporterService transporterService;

    @GetMapping()
    public String viewOrderHistory(Model model, Authentication auth, HttpSession session){
        List<OrderForm> orderFormList = adminService.getOrderList(null);
        Long countProgressingOrder = adminService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_PROGRESSING, null, null);
        model.addAttribute("countProgressingOrder", countProgressingOrder);
        session.setAttribute("countProgressingOrder", countProgressingOrder);
        Long countOrder = adminService.getNumberOrderBasedOnStatus(null, null, null);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("orderFormList", orderFormList);
        model.addAttribute("orderForm", new OrderForm());
        return "/admin/order/history";
    }

    @GetMapping("/update")
    public String updateOrderStatus(@RequestParam("id") Long id,
                                    @RequestParam("orderDspId") String orderDspId,
                                    @RequestParam("orderStatus") String orderStatus,
//            @ModelAttribute("orderForm") OrderForm orderForm,
                                    Model model,
                                    final RedirectAttributes redirectAttributes,
                                    HttpSession session) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.setOrderDspId(orderDspId);
        orderForm.setOrderStatus(orderStatus);
        Message result = adminService.updateOrderStatus(orderForm);
//        redirectAttributes.addFlashAttribute("message", result.getMessage());
//        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
//        HashMap<String, Object> message = new HashMap<>();
//        message.put("msg", result.getMessage());
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//        }
//        return "redirect:/admin/orders";
        List<OrderForm> orderFormList = adminService.getOrderList(null);
        model.addAttribute("orderFormList", orderFormList);
        Long countProgressingOrder = adminService.getNumberOrderBasedOnStatus(Consts.ORDER_STATUS_PROGRESSING, null, null);
        session.setAttribute("countProgressingOrder", countProgressingOrder);
        model.addAttribute("countProgressingOrder", countProgressingOrder);
        Long countOrder = adminService.getNumberOrderBasedOnStatus(null, null, null);
        model.addAttribute("countOrder", countOrder);
        return "/fragments/template :: order-table";
    }

    @GetMapping("/detail")
    public String viewOrderDetail(@ModelAttribute("orderId") Long orderId, Model model) {
        OrderForm orderForm = orderAdminService.getOrderDetail(orderId);
        TransporterForm transporterForm = transporterService.getTransporterInfo(orderForm.getTransporterId());
        model.addAttribute("transporterForm", transporterForm);
        model.addAttribute("orderForm", orderForm);
        return "/admin/order/detail";
    }
}
