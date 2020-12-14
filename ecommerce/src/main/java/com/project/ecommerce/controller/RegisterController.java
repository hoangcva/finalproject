package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.UserRegisterValidator;
import com.project.ecommerce.Validator.VendorRegisterValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.form.UserRegisterForm;
import com.project.ecommerce.form.VendorForm;
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
    private ProductMapper productMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRegisterValidator userRegisterValidator;
    @Autowired
    private VendorRegisterValidator vendorRegisterValidator;
    @Autowired
    private CategoryMapper categoryMapper;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String init(Model model) {
        UserRegisterForm userRegisterForm = new UserRegisterForm();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        model.addAttribute("province_list", provinceDtoList);
        model.addAttribute("user_form", userRegisterForm);
        return "register";
    }

    @RequestMapping(value = "/vendorRegister", method = RequestMethod.GET)
    public String initVendor(Model model) {
        VendorForm vendorForm = new VendorForm();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
        model.addAttribute("province_list", provinceDtoList);
        model.addAttribute("category_list", categoryDtoList);
        model.addAttribute("vendor_form", vendorForm);
        return "vendor/register";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == UserRegisterForm.class) {
            dataBinder.setValidator(userRegisterValidator);
        }else if (target.getClass() == VendorForm.class) {
            dataBinder.setValidator(vendorRegisterValidator);
        }
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerSuccessful";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(Model model,
                           @ModelAttribute("user_form") @Validated UserRegisterForm userRegisterForm,
                           BindingResult result,
                           final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("province_list", provinceDtoList);
//            model.addAttribute("user_form", userRegisterForm);
            return "register";
        }
        try {
            userService.createUser(userRegisterForm);
        }
        // Other error!!
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("user", userRegisterForm);

        return "redirect:/registerSuccessful";
    }

    @RequestMapping(value = "/saveVendor", method = RequestMethod.POST)
    public String saveVendor(Model model,
                           @ModelAttribute("vendor_form") @Validated VendorForm vendorForm,
                           BindingResult result,
                           final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("category_list", categoryDtoList);
            return "vendor/register";
        }
        try {
            userService.createVendor(vendorForm);
        }
        // Other error!!
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("category_list", categoryDtoList);
//            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("errorMessage", "Something wrong! Try again");
            return "vendor/register";
        }

        redirectAttributes.addFlashAttribute("user", vendorForm);

        return "redirect:/registerSuccessful";
    }
}
