package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.UserUpdateValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

@Controller(value = "/customer/address")
public class ManageAddressController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private UserUpdateValidator validator;
    @Autowired
    private

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == CustomerAddressForm.class) {
            dataBinder.setValidator(validator);
        }
    }

    @GetMapping(value = "/manageAddress")
    public String manageAddress(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserDto userDto = userService.getUserByUserName(userDetails.getUsername());
        List<CustomerAddressDto> customerAddressDtoList = customerAddressService.getAllAddressByCustomer(userDto.getUserName());
        model.addAttribute("address_list", customerAddressDtoList);
        return "customer/address/manageAddress";
    }

    @GetMapping(value = "/addAddress")
    public String addAddress(Model model) {
//        AddressDto =
//        ProvinceDto provinceDto = new ProvinceDto();
//        DistrictDto districtDto = new DistrictDto();
//        WardDto wardDto = new WardDto();
    }
}
