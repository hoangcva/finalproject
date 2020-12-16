package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.VendorRegisterValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/vendor")
public class VendorController {
    @Autowired
    private VendorRegisterValidator vendorRegisterValidator;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public String index(Model model) {
        return "/vendor/index";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }
        System.out.println("Target = " + target);
        if(target.getClass() == VendorForm.class) {
            dataBinder.setValidator(vendorRegisterValidator);
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String update(Model model, Authentication auth) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
        VendorForm vendorForm = vendorService.getInfo(userDetails.getUserDto().getId());
        vendorForm.setAction("update");
        model.addAttribute("vendorForm", vendorForm);
        model.addAttribute("provinceList", provinceDtoList);
        model.addAttribute("categoryList", categoryDtoList);
        return "/vendor/updateInfo";
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public String update(Model model,
                         @ModelAttribute("vendorForm") @Validated VendorForm vendorForm,
                         BindingResult bindingResult,
                         final RedirectAttributes redirectAttributes,
                         Authentication auth) {
        vendorForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
            model.addAttribute("provinceList",  provinceDtoList);
            model.addAttribute("provinceList", provinceDtoList);
            return "/vendor/updateInfo";
        }
        Message result = vendorService.updateVendor(vendorForm, auth);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/vendor/info";
    }
}
