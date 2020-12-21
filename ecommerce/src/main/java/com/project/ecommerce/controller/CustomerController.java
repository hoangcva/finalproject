package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.form.FavoriteForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
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
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private MessageAccessor messageAccessor;
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

    @PostMapping(value = "/favorite/add")
    public ResponseEntity<?> addProductToFavorite(@RequestBody FavoriteForm favoriteForm,
                                              Authentication auth) {
        Message result = customerService.addProductToFavorite(auth, favoriteForm.getProductId(), favoriteForm.getVendorId());
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if (result.isSuccess()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/favorite")
    public String viewFavoriet(Model model,
                               Authentication auth) {
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<FavoriteForm> favoriteFormList = customerService.getFavorite(customerId);
        if (favoriteFormList.size() == 0) {
            model.addAttribute("message", messageAccessor.getMessage(Consts.MSG_17_I));
            model.addAttribute("isSuccess", false);
        } else {
            model.addAttribute("favoriteFormList", favoriteFormList);
        }

        return "/customer/favoritePage";
    }

    @PostMapping(value = "/favorite/remove")
    public ResponseEntity<?> removeProductFromFavorite(@RequestBody FavoriteForm favoriteForm,
                                              Authentication auth) {
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        Message result = customerService.removeItem(favoriteForm.getProductId(), favoriteForm.getVendorId(), customerId);
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if (result.isSuccess()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
