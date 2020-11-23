package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.AddressMapper;
import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CustomerAddressForm;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("CustomerAddressService")
public class CustomerAddressServiceImpl implements ICustomerAddressService {
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private MessageAccessor messageAccessor;

    @Override
    public List<CustomerAddressDto> getAllAddressByCustomer(Long customerId) {
        return customerAddressMapper.getAllAddressByCustomerId(customerId);
    }

    @Override
    public CustomerAddressForm getAddressById(Long addressId) {
        CustomerAddressForm customerAddressForm = new CustomerAddressForm();
        CustomerAddressDto customerAddressDto = customerAddressMapper.getAddressById(addressId);
        BeanUtils.copyProperties(customerAddressDto, customerAddressForm);
        return checkWardId(customerAddressForm);
    }

    @Override
    public void updateAddress(CustomerAddressForm customerAddressForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CustomerAddressDto customerAddressDto = new CustomerAddressDto();
            BeanUtils.copyProperties(checkWardId(customerAddressForm), customerAddressDto);
            customerAddressMapper.updateAddress(customerAddressDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }
    }

    @Override
    public void createAddress(CustomerAddressForm customerAddressForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CustomerAddressDto customerAddressDto = new CustomerAddressDto();
            BeanUtils.copyProperties(checkWardId(customerAddressForm), customerAddressDto);
            customerAddressMapper.insertAddress(customerAddressDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }
    }

    @Override
    public Message deleteAddress(Long addressId) {
        Message message = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            customerAddressMapper.deleteAddress(addressId);
            transactionManager.commit(txStatus);
            message.setMessage(messageAccessor.getMessage(Consts.MSG_02_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            message.setMessage(messageAccessor.getMessage(Consts.MSG_02_E));
            message.setSuccess(false);
        }
        return message;
    }

    @Override
    public void deleteAllAddress(Long customerId) {

    }

    public List<ProvinceDto> getProvinceList() {
        return addressMapper.getAllProvince();
    }

    public List<DistrictDto> getDistrictList(@Nullable Long provinceId) {
        return addressMapper.getAllDistrict(provinceId);
    }

    public List<WardDto> getWardList(@Nullable Long provinceId,@Nullable Long districtId) {
        return addressMapper.getAllWard(provinceId, districtId);
    }

    private CustomerAddressForm checkWardId(CustomerAddressForm customerAddressForm) {
        if (customerAddressForm.getWardId() == Consts.EMPTY) {
            customerAddressForm.setWardId(null);
        } else if (customerAddressForm.getWardId() == null) {
            customerAddressForm.setWardId(Consts.EMPTY);
        }
        return customerAddressForm;
    }
}
