package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.service.ICustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Objects;

@Service("CustomerAddressService")
public class CustomerAddressServiceImpl implements ICustomerAddressService {
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<CustomerAddressDto> getAllAddressByCustomer(Long customerId) {
        return customerAddressMapper.getAllAddressByCustomerId(customerId);
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
            customerAddressDto.setFullName(customerAddressForm.getFullName());
            customerAddressDto.setId(customerAddressForm.getId());
            customerAddressDto.setProvince(customerAddressForm.getProvinceId());
            customerAddressDto.setDistrict(customerAddressForm.getDistrictId());
            customerAddressDto.setWard(customerAddressForm.getWardId());
            customerAddressMapper.updateAddress(customerAddressDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public void createAddress(CustomerAddressForm customerAddressForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CustomerAddressDto customerAddressDto = new CustomerAddressDto();
            customerAddressDto.setAddressDetail(customerAddressForm.getAddressDetail());
            customerAddressDto.setFullName(customerAddressForm.getFullName());
            customerAddressDto.setId(customerAddressForm.getId());
            customerAddressDto.setProvince(customerAddressForm.getProvinceId());
            customerAddressDto.setDistrict(customerAddressForm.getDistrictId());
            customerAddressDto.setWard(customerAddressForm.getWardId());
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

    public List<DistrictDto> getDistrictList(@Nullable Long provinceId) {
        provinceId = Objects.isNull(provinceId) ? Consts.PROVINCE_CODE_HANOI : provinceId;
        return addressMapper.getAllDistrict(provinceId);
    }

    public List<WardDto> getWardList(@Nullable Long provinceId,@Nullable Long districtId) {
        provinceId = Objects.isNull(provinceId) ? Consts.PROVINCE_CODE_HANOI : provinceId;
        districtId = Objects.isNull(districtId) ? Consts.DISTRICT_CODE_BADINH : districtId;
        return addressMapper.getAllWard(provinceId, districtId);
    }
}
