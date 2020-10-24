package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "customer/address")
public class ManageAddressController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping (value = "/manageAddress")
    public String init(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserDto userDto = userService.getUserByUserName(userDetails.getUsername());
        return "customer/address/manageAddress";
    }
}
