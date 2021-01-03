package com.project.ecommerce.controller.admin;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.CategoryValidator;
import com.project.ecommerce.Validator.TransporterValidatior;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IAdminService;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.service.impl.UserServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IProductService productService;
    @Autowired
    private CategoryValidator categoryValidator;
    @Autowired
    private TransporterValidatior transporterValidatior;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private ITransporterService transporterService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if(target == null) {
            return;
        }

        System.out.println("Target = " + target);
        if(target.getClass() == SubCategoryForm.class) {
            dataBinder.setValidator(categoryValidator);
        } else if(target.getClass() == TransporterForm.class) {
            dataBinder.setValidator(transporterValidatior);
        }
    }

    @GetMapping
    public String index(Model model) {
        List<UserDto> userDtoList = userService.getAllUser();
        model.addAttribute("user_list", userDtoList);
        return "/admin/index";
    }

//    @PostMapping(value = "/account/delete")
////    @RequestMapping(value = "/account/delete", method = RequestMethod.DELETE)
//    public String deleteUser(@RequestParam Long userId, Model model, final RedirectAttributes redirectAttributes) {
////    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
//        boolean isRemoved = userService.deleteUser(userId);
//        if(!isRemoved) {
////            List<UserDto> userDtoList = userService.getAllUser();
////            model.addAttribute("user_list", userDtoList);
//            redirectAttributes.addFlashAttribute("errorMessage", "Something wrong! Try again");
//            return "redirect:/admin";
//        }
////        List<UserDto> userDtoList = userService.getAllUser();
////        model.addAttribute("user_list", userDtoList);
//        redirectAttributes.addFlashAttribute("message", "Delete successful!");
//        return "redirect:/admin";
//    }

    @GetMapping("/account/transporter")
    public String transporterAccountManager(Model model) {
        List<TransporterForm> transporterFormList = transporterService.getTransporterList();
        model.addAttribute("transporterFormList", transporterFormList);
        return "/admin/account/transporterAccountManager";
    }

    @GetMapping("/account/vendor")
    public String vendorAccountManager(Model model,
                                       @RequestParam(value = "type", required = false) String type) {
        List<VendorForm> vendorFormList = new ArrayList<>();
        if ("all".equals(type) || type == null) {
            vendorFormList = userService.getVendorList(null);
        } else if ("deactivated".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_DEACTIVATE);
        } else if ("active".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_ACTIVATE);
        }
        model.addAttribute("vendorFormList", vendorFormList);

        if (type == null) {
            return "/admin/account/vendorAccountManager";
        } else {
            return "/fragments/template :: vendor-table";
        }
    }

    @GetMapping(value = "/account/vendor/active")
//    public String activeVendor(@RequestBody VendorForm vendorForm, Model model) {
    public String activeVendor(@RequestParam("vendorId") Long vendorId,
                               @RequestParam("enable") Boolean enable,
                               @RequestParam("radioType") String radioType,
                               Model model) {
        Message result = userService.activeVendor(vendorId, enable);

        List<VendorForm> vendorFormList = new ArrayList<>();

        String type = radioType;
        if ("all".equals(type)) {
            vendorFormList = userService.getVendorList(null);
        } else if ("deactivated".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_DEACTIVATE);
        } else if ("active".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_ACTIVATE);
        }
        model.addAttribute("vendorFormList", vendorFormList);
        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
        return "/fragments/template :: vendor-table";
//        if(!result.isSuccess()) {
//            return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
    }

    @PostMapping(value = "/account/vendor/delete")
    public String deleteVendor(@RequestBody VendorForm vendorForm, Model model) {
        Message result = userService.deleteUser(vendorForm.getVendorId());
        List<VendorForm> vendorFormList = new ArrayList<>();
        String type = vendorForm.getRadioType();
        if ("all".equals(type)) {
            vendorFormList = userService.getVendorList(null);
        } else if ("deactivated".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_DEACTIVATE);
        } else if ("active".equals(type)) {
            vendorFormList = userService.getVendorList(Consts.ACCOUNT_STATUS_ACTIVATE);
        }
        model.addAttribute("vendorFormList", vendorFormList);
        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
        return "/fragments/template :: vendor-table";
    }

    @PostMapping(value = "/account/transporter/delete")
    public String deleteTransporter(@RequestBody TransporterForm transporterForm, Model model) {
        Message result = userService.deleteUser(transporterForm.getTransporterId());
        List<TransporterForm> transporterFormList = new ArrayList<>();
        transporterFormList = transporterService.getTransporterList();
        model.addAttribute("transporterFormList", transporterFormList);
        model.addAttribute("message", result.getMessage());
        model.addAttribute("isSuccess", result.isSuccess());
        return "/fragments/template :: transporter-table";
    }

    @PostMapping(value = "/account/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteForm user, Model model) {
        Message result = userService.deleteUser(user.getUserId());
        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
    }

    @GetMapping(value = "/category/add")
    public String addCategory(Model model) {
        List<CategoryDto> categoryDtoList= productService.getAllCategory();
        SubCategoryForm subCategoryForm = new SubCategoryForm();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("subCategoryForm", subCategoryForm);
        return "/admin/addCategory";
    }

    @PostMapping(value = "/category/add")
    public String addCategory(@ModelAttribute("subCategoryForm") @Validated SubCategoryForm subCategoryForm,
                              BindingResult bindingResult,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        subCategoryForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryDtoList= productService.getAllCategory();
            model.addAttribute("categories", categoryDtoList);
            return "/admin/addCategory";
        }

        Message result = adminService.addCategory(subCategoryForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin/category/add";
    }

    @GetMapping("/transporter")
    public String addTransporter(Model model) {
        TransporterForm transporterForm = new TransporterForm();
        transporterForm.setAction(Consts.ACTION_REGISTER);
        model.addAttribute("transporterForm", transporterForm);
        return "/admin/addTransporter";
    }

    @PostMapping("/transporter/add")
    public String addTransporter(@ModelAttribute("transporterForm") @Validated TransporterForm transporterForm,
                                 BindingResult bindingResult,
                                 Model model,
                                 final RedirectAttributes redirectAttributes,
                                 Authentication auth) {
        transporterForm.setSubmitted(true);
        if (bindingResult.hasErrors()) {
            return "/admin/addTransporter";
        }
        Message result = adminService.addTransporter(transporterForm, auth);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin";
    }



    @GetMapping("/account/transporter/edit")
    public String editTransporter(Model model,
                                  @ModelAttribute("transporterId") Long transporterId) {
        TransporterForm transporterForm = transporterService.getTransporterInfo(transporterId);
        transporterForm.setAction(Consts.ACTION_UPDATE);
        model.addAttribute("transporterForm", transporterForm);
        return "/admin/account/editTransporter";

    }

    @PostMapping("/account/transporter/editTransporter")
    public String editTransporter(@ModelAttribute("transporterForm") @Validated TransporterForm TransporterForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  final RedirectAttributes redirectAttributes,
                                  Authentication auth) {
        if (bindingResult.hasErrors()) {
            return "/admin/account/editTransporter";
        }
        Message result = adminService.updateTransporterInfo(TransporterForm, auth);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
        return "redirect:/admin/account/transporter";
    }

    @RequestMapping(value = "/account/update", method = RequestMethod.GET)
    public String init(Model model,
                       @ModelAttribute("userName") String userName,
                       Authentication auth) {
        UserDto userDto = userService.getUserByUserName(userName);
        if(Consts.ROLE_USER.equals(userDto.getRole())) {
            UserUpdateForm userUpdateForm = new UserUpdateForm();
            userUpdateForm.setUserName(userName);
            userUpdateForm.setFullName(userDto.getFullName());
            userUpdateForm.setPassword(userDto.getPassword());
            userUpdateForm.setEmail(userDto.getEmail());
            userUpdateForm.setEmail(userDto.getEmail());
            userUpdateForm.nullToEmpty();
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            model.addAttribute("userUpdateForm", userUpdateForm);
            model.addAttribute("provinceList", provinceDtoList);
            return "customer/updateUserInfo";
        } else if(Consts.ROLE_VENDOR.equals(userDto.getRole())) {
            List<ProvinceDto> provinceDtoList = addressMapper.getAllProvince();
            List<CategoryDto> categoryDtoList = categoryMapper.getAllCategory();
            VendorForm vendorForm = vendorService.getInfo(userDto.getId());
            vendorForm.setAction("update");
            model.addAttribute("vendorForm", vendorForm);
            model.addAttribute("provinceList", provinceDtoList);
            model.addAttribute("categoryList", categoryDtoList);
            return "/vendor/updateInfo";
        } else if(Consts.ROLE_SHIPPER.equals(userDto.getRole())) {
            TransporterForm transporterForm = transporterService.getTransporterInfo(userDto.getId());
            transporterForm.setAction(Consts.ACTION_UPDATE);
            model.addAttribute("transporterForm", transporterForm);
            return "/admin/account/editTransporter";
        }

        return "/admin/index";
    }
}
