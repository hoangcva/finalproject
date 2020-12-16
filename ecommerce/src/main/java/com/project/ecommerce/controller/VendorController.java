package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.UserUpdateValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserDeleteForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.service.impl.UserServiceImpl;
import com.project.ecommerce.service.impl.VendorServiceImpl;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/vendor")
public class VendorController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserUpdateValidator validator;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IVendorService vendorService;

    @GetMapping
    public String index(Model model) {
        return "/vendor/index";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == VendorForm.class) {
            dataBinder.setValidator(validator);
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String init(Model model, Authentication auth) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        VendorForm vendorForm = vendorService.getInfo(userDetails.getUserDto().getId());
        vendorForm.setUserName(userDetails.getUsername());
        vendorForm.setPassword(userDetails.getPassword());
        vendorForm.setEmail(userDetails.getUserDto().getEmail());
        model.addAttribute("vendorForm", vendorForm);
        model.addAttribute("province_list", provinceDtoList);
        return "/vendor/updateInfo";
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public String updateUser(Model model,
                             @ModelAttribute("vendorForm") @Validated VendorForm vendorForm,
                             BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes,
                             HttpSession session) {
        List<ProvinceDto> provinceList = (List<ProvinceDto>) session.getAttribute("province_list");
        model.addAttribute("province_list",  provinceList);
        Message result = new Message();
        if (bindingResult.hasErrors()) {
            return "/vendor/updateInfo";
        }
        result = vendorService.updateVendor(vendorForm);

        model.addAttribute("message", result.getMessage());
        return "/vendor/index";
    }
}
