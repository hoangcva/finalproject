package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dao.VendorMapper;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;

@Service
public class VendorServiceImpl implements IVendorService {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private VendorMapper vendorMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private UserMapper userMapper;

    @Override
    public VendorForm getInfo(Long vendorId) {
        VendorDto vendorDto = userMapper.getVendorInfo(vendorId);
        VendorForm vendorForm = new VendorForm();
        BeanUtils.copyProperties(vendorDto, vendorForm);
        return vendorForm;
    }

    @Override
    public Message updateVendor(VendorForm vendorForm, Authentication auth) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Long userId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            VendorDto vendorDto = new VendorDto();
            BeanUtils.copyProperties(vendorForm, vendorDto);
            vendorDto.setVendorId(userId);

            UserDto userDto = vendorDto;
            userDto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            userDto.setId(userId);
            userMapper.updateVendor(vendorDto);
            userMapper.updateUser(userDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_08_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_08_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message createVendor(VendorForm vendorForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            VendorDto vendorDto = new VendorDto();
            BeanUtils.copyProperties(vendorForm, vendorDto);
            vendorDto.setRole(Consts.ROLE_VENDOR);
            vendorDto.setPassword(encoder.encode(vendorForm.getPassword()));
            userMapper.createUser(vendorDto);
            userMapper.createVendor(vendorDto);
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_09_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_09_E, ""));
            result.setSuccess(false);
        }
        return result;
    }
}
