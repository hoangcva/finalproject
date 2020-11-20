package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.CartLineInfoForm;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Controller
public class CartController {
    @Autowired
    private ICartService cartService;

    @PostMapping(value = "/user/addProductToCart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartLineInfoForm CartLineInfoForm, Authentication auth) {
        Message result = cartService.addProductToCart(CartLineInfoForm, auth);
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if (result.isSuccess()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/viewCart")
    public String viewCart(Authentication auth, Model model) {
        CartInfoForm cartInfoForm = new CartInfoForm();
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        cartInfoForm = cartService.getCart(userDetails.getUserDto().getId());
        model.addAttribute("cartInfoForm", cartInfoForm);

        return "customer/cart";
    }
}
