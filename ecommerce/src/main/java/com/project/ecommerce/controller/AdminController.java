package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserDeleteForm;
import com.project.ecommerce.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping(value = "/delete")
////    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public String deleteUser(@RequestParam Long userId, Model model, final RedirectAttributes redirectAttributes) {
////    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
//        boolean isRemoved = userService.deleteUser(userId);
//        if(!isRemoved) {
////            List<UserDto> userDtoList = userService.getAllUser();
////            model.addAttribute("user_list", userDtoList);
//            redirectAttributes.addFlashAttribute("errorMessage", "Something wrong! Try again");
//            return "redirect:/admin";
//        }
////        List<UserDto> userDtoList = userService.getAllUser();
////        model.addAttribute("user_list", userDtoList);
//        redirectAttributes.addFlashAttribute("message", "Delete successful!");
//        return "redirect:/admin";
//    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteForm user) {
        boolean isRemoved = userService.deleteUser(user.getUserId());
        if(!isRemoved) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
