package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerAddressDto;

import java.util.List;

public interface ICustomerAddressService {
    List<CustomerAddressDto> getAllAddressByCustomer(String customerName);
    CustomerAddressDto getAddressById(Long addressId);
}
