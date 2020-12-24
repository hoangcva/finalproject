package com.project.ecommerce.service;

import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.form.VendorStatisticForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

public interface IVendorService {
    VendorForm getInfo(Long vendorId);
    Message updateVendor(VendorForm vendorForm, Authentication auth);
    Message createVendor(VendorForm vendorForm);
    VendorStatisticForm statistic(Long vendorId);
}
