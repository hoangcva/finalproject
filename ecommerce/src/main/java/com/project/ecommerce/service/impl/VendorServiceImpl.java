package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.VendorMapper;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IVendorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements IVendorService {
    @Autowired
    private VendorMapper vendorMapper;
    @Override
    public VendorForm getInfo(Long vendorId) {
        VendorDto vendorDto = vendorMapper.getInfo(vendorId);
        VendorForm vendorForm = new VendorForm();
        BeanUtils.copyProperties(vendorDto, vendorForm);
        return vendorForm;
    }
}
