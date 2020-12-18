package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.util.Message;
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
                              Model model,
                              Authentication auth) {
        CommentForm commentForm = new CommentForm();
        commentForm.setProductId(productId);
        commentForm.setVendorId(vendorId);
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        commentForm.setCustomerId(customerId);
        customerService.createComment(commentForm);
        model.addAttribute("commentForm", commentForm);
        return "/customer/createComment";
    }

    @PostMapping
    public String saveComment(@ModelAttribute("commentForm") CommentForm commentForm,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Message result = customerService.saveComment(commentForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/customer/success";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "/customer/success";
    }

}
