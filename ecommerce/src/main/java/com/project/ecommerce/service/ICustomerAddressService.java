package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerAddressDto;
import com.project.ecommerce.dto.DistrictDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.WardDto;

import java.util.List;

public interface ICustomerAddressService {
    List<CustomerAddressDto> getAllAddressByCustomer(String customerName);
    CustomerAddressDto getAddressById(Long addressId);
    List<ProvinceDto> getProvinceList();
    List<DistrictDto> getDistrictList(Long provinceId);
    List<WardDto> getWardList(Long provinceId, Long districtId);
}
