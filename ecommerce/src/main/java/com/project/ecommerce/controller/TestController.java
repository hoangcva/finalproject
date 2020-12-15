package com.project.ecommerce.controller;

import com.project.ecommerce.dto.TestDto;
import com.project.ecommerce.form.TestForm;
import com.project.ecommerce.service.ITestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping(value = "/test/view")
    public String testGet(Model model) {
        TestForm testForm = new TestForm();
        TestDto testDto = testService.get();
        BeanUtils.copyProperties(testDto,testForm);
        model.addAttribute("testForm", testForm);
        return "/test";
    }

    @PostMapping("/test/add")
    public String testPost(@ModelAttribute("testForm") TestForm testForm,
            Model model, RedirectAttributes redirectAttributes) {
        testService.save(testForm);
        return "redirect:/test/view";
    }
}
