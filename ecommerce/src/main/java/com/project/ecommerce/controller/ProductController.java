package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@RequestBody ProductDto productDto) {
        // load danh sach category

        // load danh sach sub-category
        return "/addProduct";
    }
}
