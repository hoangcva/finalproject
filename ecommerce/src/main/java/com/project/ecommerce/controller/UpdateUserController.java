package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.UserUpdateValidator;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.service.IUserService;
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

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    public String init(Model model, HttpSession session, Authentication auth) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        UserUpdateForm user = new UserUpdateForm();
        user.setUserName(userDetails.getUsername());
        user.setFullName(userDetails.getUserDto().getFullName());
        user.setGender(userDetails.getUserDto().getGender());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getUserDto().getEmail());
        user.nullToEmpty();
        List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
        model.addAttribute("user_update_form", user);
        model.addAttribute("province_list", provinceDtoList);
//        session.setAttribute("user_update_form", user);
        session.setAttribute("province_list", provinceDtoList);
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

        System.out.println("Target = " + target);
        if(target.getClass() == UserUpdateForm.class) {
            dataBinder.setValidator(validator);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(Model model,
                           @ModelAttribute("user_update_form") @Validated UserUpdateForm userUpdateForm,
                           BindingResult result,
                           final RedirectAttributes redirectAttributes,
                           HttpSession session) {
        List<ProvinceDto> provinceList = (List<ProvinceDto>) session.getAttribute("province_list");
        model.addAttribute("province_list",  provinceList);

        if (result.hasErrors()) {
            return "customer/updateUserInfo";
        }

        try {
            userService.updateUser(userUpdateForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "customer/updateUserInfo";
        }

        model.addAttribute("message", "update successfully");
        return "customer/updateUserInfo";

    }
}
