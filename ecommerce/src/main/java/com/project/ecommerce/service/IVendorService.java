package com.project.ecommerce.service;

import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.util.Message;

public interface IVendorService {
    VendorForm getInfo(Long vendorId);
    Message updateVendor(VendorForm vendorForm);
}
