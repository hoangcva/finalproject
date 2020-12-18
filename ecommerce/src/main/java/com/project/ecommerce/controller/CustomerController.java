package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/comment")
    public String saveComment(@RequestParam("productId") Long productId,
                              @RequestParam("vendorId") Long vendorId,
                              @RequestParam("orderId") Long orderId,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              Authentication auth) {
        CommentForm commentForm = new CommentForm();
        commentForm.setProductId(productId);
        commentForm.setVendorId(vendorId);
        commentForm.setOrderId(orderId);
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        commentForm.setCustomerId(customerId);
        commentForm = customerService.getComment(commentForm);
//        if (commentForm.getId() == null) {
//            Message result = customerService.createComment(commentForm, auth);
//            if (!result.isSuccess()) {
//                redirectAttributes.addFlashAttribute("message", result.getMessage());
//                redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
//                return "redirect:/customer/success";
//            }
//        }
        model.addAttribute("commentForm", commentForm);
        return "/customer/createComment";
    }

    @PostMapping("/comment/save")
    public String saveComment(@ModelAttribute("commentForm") CommentForm commentForm,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              Authentication auth) {
        Message result;
        if (commentForm.getId() == null) {
            result = customerService.createComment(commentForm, auth);
        } else {
            result = customerService.saveComment(commentForm);
        }
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/customer/success";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "/customer/success";
    }

}
