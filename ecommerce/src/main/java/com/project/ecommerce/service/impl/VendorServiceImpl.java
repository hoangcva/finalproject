package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dao.VendorMapper;
import com.project.ecommerce.dto.SubCategoryDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IVendorService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class VendorServiceImpl implements IVendorService {
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
        VendorDto vendorDto = vendorMapper.getInfo(vendorId);
        VendorForm vendorForm = new VendorForm();
        BeanUtils.copyProperties(vendorDto, vendorForm);
        return vendorForm;
    }

    @Override
    public Message updateVendor(VendorForm vendorForm) {

        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            VendorDto vendorDto = new VendorDto();
            BeanUtils.copyProperties(vendorForm, vendorDto);

            UserDto userDto = vendorDto;
            vendorMapper.updateVendor(vendorDto);
            userMapper.updateUser(userDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_E, ""));
            result.setSuccess(false);
        }
        return result;
    }
}
