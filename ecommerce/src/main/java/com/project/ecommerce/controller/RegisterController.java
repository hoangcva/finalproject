package com.project.ecommerce.controller;

import com.project.ecommerce.dao.ProvinceMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserForm;
import com.project.ecommerce.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private IRegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String init(Model model) {
        UserForm userForm = new UserForm();
        List<ProvinceDto> provinceDtoList = provinceMapper.getAllProvince();
        model.addAttribute("provice_list", provinceDtoList);
        model.addAttribute("user_form", userForm);
        return "register";
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerSuccessful";
    }

    @PostMapping(value = "/saveRegister")
    public String saveRegister(Model model,
                               @ModelAttribute("userForm") @Validated UserForm userForm,
                               BindingResult result,
                               final RedirectAttributes redirectAttributes) {
        UserDto existing = registerService.findUserByEmail(userForm.getEmail());
        if(existing != null) {
            result.rejectValue("email", null, "This email address is already used by");
        }

        // Validate result
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = provinceMapper.getAllProvince();
            model.addAttribute("provice_list", provinceDtoList);
            return "register";
        }
        try {
            registerService.createUser(userForm);
        }
        // Other error!!
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = provinceMapper.getAllProvince();
            model.addAttribute("provice_list", provinceDtoList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "register";
        }

//        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:/register?success";
    }
}
