package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.service.ICustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service("CustomerAddressService")
public class CustomerAddressServiceImpl implements ICustomerAddressService {
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<CustomerAddressDto> getAllAddressByCustomer(String customerName) {
        return customerAddressMapper.getAllAddressByCustomer(customerName);
    }

    @Override
    public CustomerAddressDto getAddressById(Long addressId) {
        return customerAddressMapper.getAddressById(addressId);
    }

    public void updateAddress(CustomerAddressForm customerAddressForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CustomerAddressDto customerAddressDto = new CustomerAddressDto();
            customerAddressDto.setAddressDetail(customerAddressForm.getAddressDetail());
            customerAddressDto.setCustomerName(customerAddressForm.getCustomerName());
            customerAddressDto.setId(customerAddressForm.getId());
            customerAddressDto.setProvince(customerAddressForm.getProvince());
            customerAddressDto.setDistrict(customerAddressForm.getDistrict());
            customerAddressDto.setWard(customerAddressForm.getWard());
            customerAddressMapper.updateAddress(customerAddressDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public void insertAddress(CustomerAddressForm customerAddressForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CustomerAddressDto customerAddressDto = new CustomerAddressDto();
            customerAddressDto.setAddressDetail(customerAddressForm.getAddressDetail());
            customerAddressDto.setCustomerName(customerAddressForm.getCustomerName());
            customerAddressDto.setId(customerAddressForm.getId());
            customerAddressDto.setProvince(customerAddressForm.getProvince());
            customerAddressDto.setDistrict(customerAddressForm.getDistrict());
            customerAddressDto.setWard(customerAddressForm.getWard());
            customerAddressMapper.updateAddress(customerAddressDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public void deleteAddress(Long addressId) {

    }

    public List<ProvinceDto> getProvinceList() {
        return addressMapper.getAllProvince();
    }

    public List<DistrictDto> getDistrictList(Long provinceId) {
        return addressMapper.getAllDistrict(provinceId);
    }

    public List<WardDto> getWardList(Long provinceId, Long districtId) {
        return addressMapper.getAllWard(provinceId, districtId);
    }
}
