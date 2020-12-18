package com.project.ecommerce.controller;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.Validator.CategoryValidator;
import com.project.ecommerce.Validator.TransporterValidatior;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.IAdminService;
import com.project.ecommerce.service.IProductService;
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

import java.util.HashMap;
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

    @GetMapping
    public String index(Model model) {
        List<UserDto> userDtoList = userService.getAllUser();
        model.addAttribute("user_list", userDtoList);
        return "/admin/index";
    }

//    @PostMapping(value = "/delete")
////    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
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

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteForm user) {
        boolean isRemoved = userService.deleteUser(user.getUserId());
        if(!isRemoved) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
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

    @GetMapping("/transporter")
    public String addTransporter(Model model) {
        TransporterForm transporterForm = new TransporterForm();
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

    @GetMapping("/orders")
    public String viewOrderHistory(Model model, Authentication auth){
        List<OrderForm> orderFormList = adminService.getOrderList(null);
        model.addAttribute("orderFormList", orderFormList);
        model.addAttribute("orderForm", new OrderForm());
        return "/admin/order/history";
    }

    @GetMapping("/order/update")
    public String updateOrderStatus(@RequestParam("id") Long id,
                                    @RequestParam("orderDspId") String orderDspId,
                                    @RequestParam("orderStatus") String orderStatus,
//            @ModelAttribute("orderForm") OrderForm orderForm,
                                  Model model,
                                  final RedirectAttributes redirectAttributes) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(id);
        orderForm.setOrderDspId(orderDspId);
        orderForm.setOrderStatus(orderStatus);
        Message result = adminService.updateOrderStatus(orderForm);
        redirectAttributes.addFlashAttribute("message", result.getMessage());
        redirectAttributes.addFlashAttribute("isSuccess", result.isSuccess());
//        HashMap<String, Object> message = new HashMap<>();
//        message.put("msg", result.getMessage());
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//        }
        return "redirect:/admin/orders";
    }
}
