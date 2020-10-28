package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.UserValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.form.RegisterForm;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String init(Model model) {
        RegisterForm registerForm = new RegisterForm();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        model.addAttribute("province_list", provinceDtoList);
        model.addAttribute("user_form", registerForm);
        return "register";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == RegisterForm.class) {
            dataBinder.setValidator(userValidator);
        }
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerSuccessful";
    }

    @RequestMapping(value = "/saveRegister", method = RequestMethod.POST)
    public String saveRegister(Model model,
                               @ModelAttribute("user_form") @Validated RegisterForm registerForm,
                               BindingResult result,
                               final RedirectAttributes redirectAttributes) {

//        UserDto existing = userService.findUserByEmail(registerForm.getEmail());
//        if(existing != null) {
//            result.rejectValue("email", null, "This email address is already used by");
//        }

        // Validate result
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("province_list", provinceDtoList);
            return "register";
        }
        try {
            userService.createUser(registerForm);
        }
        // Other error!!
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("user", registerForm);

        return "redirect:/registerSuccessful";
    }
}
