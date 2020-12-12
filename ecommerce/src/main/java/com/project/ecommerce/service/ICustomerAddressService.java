package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerAddressDto;
import com.project.ecommerce.dto.DistrictDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.WardDto;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICustomerAddressService {
    List<CustomerAddressDto> getAllAddressByCustomer(Long customerId);
    CustomerAddressForm getAddressById(Long addressId);
    List<ProvinceDto> getProvinceList();
    List<DistrictDto> getDistrictList(Long provinceId);
    List<WardDto> getWardList(Long provinceId, Long districtId);
    void createAddress(CustomerAddressForm customerAddressForm, Long customerId);
    void updateAddress(CustomerAddressForm customerAddressForm);
    Message deleteAddress(Long addressId);
    void deleteAllAddress(Long customerId);
    Message setDefault(Long addressId, Authentication auth);
    CustomerAddressDto getDefault(Long customerId);
}
