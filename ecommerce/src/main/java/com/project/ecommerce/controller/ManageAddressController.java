package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.AddressValidator;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
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

import java.util.HashMap;
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
    public String manageAddress(Model model, Authentication auth) {
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        Long customerId = userDetails.getUserDto().getId();
//        UserDto userDto = userService.getUserByUserName(userDetails.getUsername());
        List<CustomerAddressDto> customerAddressDtoList = customerAddressService.getAllAddressByCustomer(customerId);
        model.addAttribute("address_list", customerAddressDtoList);
        return "customer/address/manageAddressPage";
    }

    @GetMapping(value = "/initAddAddress")
    public String initCreateAddress(Model model) {
        CustomerAddressForm addressForm = new CustomerAddressForm();
        model.addAttribute("address_form", addressForm);
        initData(model);
        return "/customer/address/addAddressPage";
    }

    @GetMapping(value = "/edit")
    public String editAddress(Model model, @ModelAttribute("id") long addressId) {
        CustomerAddressForm addressForm = customerAddressService.getAddressById(addressId);
        model.addAttribute("address_form", addressForm);
        initData(model);
        return "/customer/address/editAddress";
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
                                @ModelAttribute("address_form") @Validated CustomerAddressForm customerAddressForm,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes,
                                Authentication auth,
                                MessageAccessor messageAccessor) {
        // Validate result
        if (result.hasErrors()) {
            initData(model);
            return "/customer/address/addAddressPage";
        }
        try {
            UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
            Long customerId = userDetails.getUserDto().getId();
            customerAddressForm.setCustomerId(customerId);
            customerAddressService.createAddress(customerAddressForm, customerId);
        }
        // Other error!!
        catch (Exception e) {
            initData(model);
            model.addAttribute("errorMessage", messageAccessor.getMessage(Consts.MSG_02_E));
            return "/customer/address/addAddressPage";
        }

        redirectAttributes.addFlashAttribute("message", "Create address successful");
        return "redirect:/customer/address/manageAddress";
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteAddress(@RequestBody CustomerAddressForm customerAddressForm) {
        Message result = customerAddressService.deleteAddress(customerAddressForm.getId());
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if(!result.isSuccess()) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping(value = "updateAddress")
    public String updateAddress(Model model,
                                @ModelAttribute("user_form") @Validated CustomerAddressForm customerAddressForm,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes) {
        // Validate result
        if (result.hasErrors()) {
            initData(model);
            return "/customer/address/addAddressPage";
        }
        try {
            customerAddressService.updateAddress(customerAddressForm);
        }
        // Other error!!
        catch (Exception e) {
            initData(model);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/customer/address/addAddressPage";
        }

        redirectAttributes.addFlashAttribute("message", "Create address successful");
        return "redirect:/customer/address/manageAddress";
    }

    private void initData(Model model) {
        List<ProvinceDto> provinceDtoList = customerAddressService.getProvinceList();
        List<DistrictDto> districtDtoList = customerAddressService.getDistrictList(null);
        List<WardDto> wardDtoList = customerAddressService.getWardList(null, null);
        model.addAttribute("province_list", provinceDtoList);
        model.addAttribute("district_list", districtDtoList);
        model.addAttribute("ward_list", wardDtoList);
    }

    @PostMapping(value = "/setDefault")
    public ResponseEntity<?> setDefault(@RequestBody CustomerAddressForm customerAddressForm,
                                        Authentication auth) {
        Message result = customerAddressService.setDefault(customerAddressForm.getId(), auth);
        HashMap<String, Object> message = new HashMap<>();
        message.put("msg", result.getMessage());
        if(!result.isSuccess()) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
