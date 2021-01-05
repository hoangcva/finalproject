package com.project.ecommerce.controller.customer;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/customer/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @PostMapping(value = "/add-product")
    public ResponseEntity<?> addProductToCart(@RequestBody CartLineInfoForm CartLineInfoForm,
                                              HttpSession session,
                                              Authentication auth) {
        Message result = cartService.addProductToCart(CartLineInfoForm, auth);
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        ;
        if (result.isSuccess()) {
            Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            int quantityTotal = cartService.getCart(customerId).getQuantityTotal();
            session.setAttribute("quantityTotal", quantityTotal);
            message.put("quantityTotal", quantityTotal);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/view")
    public String viewCart(Authentication auth, Model model) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        CartInfoForm cartInfoForm = cartService.getCart(userDetails.getUserDto().getId());
        model.addAttribute("cartInfoForm", cartInfoForm);
        model.addAttribute("message", cartInfoForm.getResult().getMessage());
        model.addAttribute("isSuccess", cartInfoForm.getResult().isSuccess());
        return "customer/cartPage";
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateCart(@RequestBody CartLineInfoForm CartLineInfoForm,
                                        HttpSession session,
                                        Authentication auth) {
        Message result = cartService.updateQuantity(CartLineInfoForm);
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if (result.isSuccess()) {
            Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            int quantityTotal = cartService.getCart(customerId).getQuantityTotal();
            session.setAttribute("quantityTotal", quantityTotal);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/remove")
    public ResponseEntity<?> removeProduct(@RequestBody CartLineInfoForm CartLineInfoForm,
                                           HttpSession session,
                                           Authentication auth) {
        Message result = cartService.removeProduct(CartLineInfoForm.getId());
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if (result.isSuccess()) {
            Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            int quantityTotal = cartService.getCart(customerId).getQuantityTotal();
            session.setAttribute("quantityTotal", quantityTotal);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping(value = "/save")
//    public ResponseEntity<?> saveCart(@RequestBody CartInfoForm CartInfoForm) {
//        Message result = new Message();
//        HashMap<String, Object> message = new HashMap<>();
//        message.put("msg", result.getMessage());
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//        }
//    }
}
