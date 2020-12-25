package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.PassWordValidator;
import com.project.ecommerce.Validator.UserUpdateValidator;
import com.project.ecommerce.Validator.VendorRegisterValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.PasswordForm;
import com.project.ecommerce.form.UserForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UpdateUserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserUpdateValidator validator;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private PassWordValidator passWordValidator;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private VendorRegisterValidator vendorRegisterValidator;

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    public String init(Model model, HttpSession session, Authentication auth) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        UserUpdateForm user = new UserUpdateForm();
        user.setUserName(userDetails.getUsername());
        user.setFullName(userDetails.getUserDto().getFullName());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getUserDto().getEmail());
        user.nullToEmpty();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        model.addAttribute("userUpdateForm", user);
        model.addAttribute("provinceList", provinceDtoList);
//        session.setAttribute("userUpdateForm", user);
//        session.setAttribute("provinceList", provinceDtoList);
//        model.addAttribute("session", session);
        return "customer/updateUserInfo";
    }

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }
        if(target.getClass() == UserUpdateForm.class) {
            dataBinder.setValidator(validator);
        } else if(target.getClass() == PasswordForm.class) {
            dataBinder.setValidator(passWordValidator);
        } else         if(target.getClass() == VendorForm.class) {
            dataBinder.setValidator(vendorRegisterValidator);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(Model model,
                             @ModelAttribute("userUpdateForm") @Validated UserUpdateForm userUpdateForm,
                             BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes,
                             HttpSession session,
                             Authentication auth,
                             @ModelAttribute("userName") String userName) {
//        List<ProvinceDto> provinceList = (List<ProvinceDto>) session.getAttribute("provinceList");
        userUpdateForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("provinceList",  provinceDtoList);
            return "customer/updateUserInfo";
        }
        Message result = userService.updateUser(userUpdateForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        if (Consts.ROLE_ADMIN.equals(((UserDetailsDto) auth.getPrincipal()).getUserDto().getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/user/update";
    }

    @GetMapping("/user/update/password")
    public String changePassWord(Model model,
                                Authentication auth,
                                @ModelAttribute("vendorForm") VendorForm vendorForm) {
        UserDetailsDto user = (UserDetailsDto) auth.getPrincipal();
        String userName = user.getUsername();
        Long userId = user.getUserDto().getId();
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setUserName(userName);
        passwordForm.setUserId(userId);
        model.addAttribute("passwordForm", passwordForm);
        return "changePassword";
    }

    @PostMapping("/user/update/password/change")
    public String changePassword(Model model,
                                 @ModelAttribute("passwordForm") @Validated PasswordForm passwordForm,
                                 BindingResult bindingResult,
                                 final RedirectAttributes redirectAttributes,
                                 Authentication auth) {
        passwordForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            return "changePassword";
        }
        Message result = userService.changePassword(passwordForm, auth);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        String role = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getRole();
        if (Consts.ROLE_USER.equals(role)) {
            return "redirect:/customer/success";
        } else if (Consts.ROLE_VENDOR.equals(role)) {
            return "redirect:/vendor/success";
        } else if (Consts.ROLE_ADMIN.equals(role)) {
            return "redirect:/admin/index";
        }
        return "redirect:success";
    }

    @RequestMapping(value = "/user/update/vendor", method = RequestMethod.POST)
    public String update(Model model,
                         @ModelAttribute("vendorForm") @Validated VendorForm vendorForm,
                         BindingResult bindingResult,
                         final RedirectAttributes redirectAttributes,
                         Authentication auth,
                         @ModelAttribute("userName") String userName) {
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
        if (Consts.ROLE_ADMIN.equals(((UserDetailsDto) auth.getPrincipal()).getUserDto().getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/vendor/info";
    }
}
