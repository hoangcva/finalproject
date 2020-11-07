package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserDeleteForm;
import com.project.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/vendor")
public class VendorController {

    @GetMapping
    public String index(Model model) {
        return "/vendor/index";
    }
}
