package com.project.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping(value="/login")
    public String login(@RequestParam(required = false) String message, Model model) throws Exception {
        if (message != null && !message.isEmpty()) {
            if (message.equals("logout")) {
                model.addAttribute("message", "You have been logged out!");
                model.addAttribute("isSuccess", true);
            }
            else if (message.equals("error")) {
                model.addAttribute("message", "Invalid username or password!");
                model.addAttribute("isSuccess", false);
            }
        }

        return "login";
    }
    @RequestMapping("/user")
    public String user() {
        return "customer/user";
    }
//    @RequestMapping("/admin")
//    public String admin() {
//        return "admin";
//    }
    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }

}
