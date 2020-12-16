package com.project.ecommerce.service;

import com.project.ecommerce.form.VendorForm;

public interface IVendorService {
    VendorForm getInfo(Long vendorId);
}
