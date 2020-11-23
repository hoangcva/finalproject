package com.project.ecommerce.controller;

import com.project.ecommerce.Validator.AddressValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.service.ICustomerAddressService;
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

import java.util.List;

@Controller
@RequestMapping(value = "/customer/address")
public class ManageAddressController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private AddressValidator validator;

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
        List<CustomerAddressDto> customerAddressDtoList = customerAddressService.getAllAddressByCustomer(userDto.getId());
        model.addAttribute("address_list", customerAddressDtoList);
        return "customer/address/manageAddressPage";
    }

    @GetMapping(value = "/initAddAddress")
    public String initCreateAddress(Model model) {
        CustomerAddressForm addressForm = new CustomerAddressForm();
        List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
        List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
        List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
        model.addAttribute("province_list", provinceDtoList);
        model.addAttribute("district_list", districtDtoList);
        model.addAttribute("ward_list", wardDtoList);
        model.addAttribute("address_form", addressForm);
        return "/customer/address/addAddressPage";
    }

    @GetMapping(value = "/getProvince")
    public List<ProvinceDto> getProvince() {
        return customerAddressService.getProvinceList();
    }

    @GetMapping(value = "/getDistrict")
    public List<DistrictDto> getDistrict(Long provinceId) {
        return customerAddressService.getDistrictList(provinceId);
    }

    @GetMapping(value = "/getWard")
    public List<WardDto> getWard(Long provinceId, Long districtId) {
        return customerAddressService.getWardList(provinceId, districtId);
    }

    @PostMapping(value = "/addAddress")
    public String createAddress(Model model,
                                @ModelAttribute("user_form") @Validated CustomerAddressForm customerAddressForm,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {
//            List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
//            List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
//            List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
//            model.addAttribute("province_list", provinceDtoList);
//            model.addAttribute("district_list", districtDtoList);
//            model.addAttribute("ward_list", wardDtoList);
            return "redirect:/customer/address/initAddAddress";
        }
        try {
            customerAddressService.createAddress(customerAddressForm);
        }
        // Other error!!
        catch (Exception e) {
//            List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
//            List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
//            List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
//            model.addAttribute("province_list", provinceDtoList);
//            model.addAttribute("district_list", districtDtoList);
//            model.addAttribute("ward_list", wardDtoList);
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/customer/address/initAddAddress";
        }

        redirectAttributes.addFlashAttribute("message", "Create address successful");
        return "redirect:/customer/address/manageAddress";
    }

    @DeleteMapping(value = "/deleteAddress")
    public String deleteAddress(Model model, @PathVariable("address_id") Long addressId) {
        customerAddressService.deleteAddress(addressId);
        return "redirect:/customer/address/manageAddress";
    }

    @PostMapping(value = "updateAddress")
    public String updateAddress(Model model,
                                @ModelAttribute("user_form") @Validated CustomerAddressForm customerAddressForm,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {
            List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
            List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
            List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("district_list", districtDtoList);
            model.addAttribute("ward_list", wardDtoList);
            return "addAddressPage";
        }
        try {
            customerAddressService.updateAddress(customerAddressForm);
        }
        // Other error!!
        catch (Exception e) {
            List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
            List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
            List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
            model.addAttribute("province_list", provinceDtoList);
            model.addAttribute("district_list", districtDtoList);
            model.addAttribute("ward_list", wardDtoList);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "addAddressPage";
        }

        redirectAttributes.addFlashAttribute("message", "Create address successful");
        return "redirect:/customer/address/manageAddress";
    }
}
