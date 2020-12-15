package com.project.ecommerce.service;

import com.project.ecommerce.dto.TestDto;
import com.project.ecommerce.form.TestForm;

public interface ITestService {
    void save(TestForm testForm);
    TestDto get();
}
