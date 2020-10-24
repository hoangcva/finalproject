package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dto.CustomerAddressDto;
import com.project.ecommerce.service.ICustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerAddressServiceImpl implements ICustomerAddressService {
    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public List<CustomerAddressDto> getAllAddressByCustomer(String customerName) {
        return customerAddressMapper.getAllAddressByCustomer(customerName);
    }

    public CustomerAddressDto getAddressById(Long addressId) {
        return customerAddressMapper.getAddressById(addressId);
    }

    public void updateAddress() {

    }

    public void insertAddress() {}

    public void deleteAddress(Long addressId) {

    }
}
