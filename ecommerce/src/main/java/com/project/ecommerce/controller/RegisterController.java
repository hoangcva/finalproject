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
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
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
    private UserRegisterValidator userRegisterValidator;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private VendorRegisterValidator vendorRegisterValidator;

    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public String init(Model model) {
        UserRegisterForm userRegisterForm = new UserRegisterForm();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        model.addAttribute("provinceList", provinceDtoList);
        model.addAttribute("userRegisterForm", userRegisterForm);
        return "register";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }
        if(target.getClass() == UserRegisterForm.class) {
            dataBinder.setValidator(userRegisterValidator);
        } else if(target.getClass() == VendorForm.class) {
            dataBinder.setValidator(vendorRegisterValidator);
        }
    }

    @RequestMapping("/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerSuccessful";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(Model model,
                           @ModelAttribute("userRegisterForm") @Validated UserRegisterForm userRegisterForm,
                           BindingResult result,
                           final RedirectAttributes redirectAttributes) {
        userRegisterForm.setSubmitted(true);
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("provinceList", provinceDtoList);
//            model.addAttribute("user_form", userRegisterForm);
            return "register";
        }
        try {
            userService.createUser(userRegisterForm);
        }
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("provinceList", provinceDtoList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("user", userRegisterForm);

        return "redirect:/registerSuccessful";
    }

    @GetMapping(value = "/registerVendor")
    public String initVendor(Model model) {
        VendorForm vendorForm = new VendorForm();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
        vendorForm.setAction("register");
        model.addAttribute("provinceList", provinceDtoList);
        model.addAttribute("categoryList", categoryDtoList);
        model.addAttribute("vendorForm", vendorForm);
        return "/vendor/register";
    }

    @PostMapping(value = "/saveVendor")
    public String saveVendor(Model model,
                             @ModelAttribute("vendorForm") @Validated VendorForm vendorForm,
                             BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes) {
        // Validate bindingResult
        vendorForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
            model.addAttribute("provinceList", provinceDtoList);
            model.addAttribute("categoryList", categoryDtoList);
            return "/vendor/register";
        }
        Message result = vendorService.createVendor(vendorForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/login";
    }

}
