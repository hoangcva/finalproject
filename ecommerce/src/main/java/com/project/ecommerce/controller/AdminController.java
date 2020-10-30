package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String index(Model model) {
        List<UserDto> userDtoList = userService.getAllUser();
        model.addAttribute("user_list", userDtoList);
        return "/admin/index";
    }

    @PostMapping(value = "/delete")
//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestParam String userName) {
//    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
        boolean isRemoved = userService.deleteUser(userName);
        if(!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }
}
