package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.PasswordForm;
import com.project.ecommerce.form.UserRegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private IProductService productService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto user = userMapper.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        return new UserDetailsDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return null;
    }

    @Override
    public Message createUser(UserRegisterForm userRegisterForm) {
        Message result = new Message();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setUserName(userRegisterForm.getUserName());
            userDto.setPassword(encoder.encode(userRegisterForm.getPassword()));
            userDto.setEmail(userRegisterForm.getEmail());
            userDto.setFullName(userRegisterForm.getFullName());
            userDto.setRole(Consts.ROLE_USER);
            userMapper.createUser(userDto);
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_29_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_29_E));
            result.setSuccess(false);
        }
        return result;
    }

    public UserDto getUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    public Message updateUser(UserUpdateForm userUpdateForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setUserName(userUpdateForm.getUserName());
            userDto.setEmail(userUpdateForm.getEmail());
            userDto.setFullName(userUpdateForm.getFullName());
            userMapper.updateUser(userDto);
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_24_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_24_E));
            result.setSuccess(false);
        }
        return  result;
    }

    public List<UserDto> getAllUser() {
        return userMapper.getAllUser();
    }

    public Message deleteUser(Long userId) {
        Message result = new Message("", true);
        UserDto userDto = userMapper.findUserById(userId);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (Consts.ROLE_USER.equals(userDto.getRole())) {
                customerAddressMapper.deleteALLAddressByUser(userId);
                userMapper.deleteUser(userId);
            } else if (Consts.ROLE_VENDOR.equals(userDto.getRole())) {
                userMapper.deleteVendor(userId);
                userMapper.deleteUser(userId);
            } else if (Consts.ROLE_SHIPPER.equals(userDto.getRole())) {
                userMapper.deleteTransporter(userId);
                userMapper.deleteUser(userId);
            } else {
                transactionManager.rollback(txStatus);
                result.setMessage(messageAccessor.getMessage(Consts.MSG_20_E));
                result.setSuccess(false);
                return result;
            }
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_20_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_20_E));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<VendorForm> getVendorList(Boolean enable) {
        List<VendorDto> vendorDtoList = userMapper.getVendorList(enable);
        List<VendorForm> vendorFormList = new ArrayList<>();
        for (VendorDto vendorDto : vendorDtoList) {
            VendorForm vendorForm = new VendorForm();
            BeanUtils.copyProperties(vendorDto, vendorForm);
            vendorFormList.add(vendorForm);
        }
        return vendorFormList;
    }

    @Override
    public Message activeVendor(Long vendorId, Boolean enable) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        VendorDto vendorDto = userMapper.getVendorInfo(vendorId);
        try {
            userMapper.activeVendor(vendorId, enable);
            productService.activateVendorProduct(vendorId, enable);
            //commit
            transactionManager.commit(txStatus);
            if (enable) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_18_I, vendorDto.getUserName()));
            } else {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_19_I, vendorDto.getUserName()));
            }
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            if (enable) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_18_E, vendorDto.getUserName()));
            } else {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_19_E, vendorDto.getUserName()));
            }
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message changePassword(PasswordForm passwordForm, Authentication auth) {
        Message result = new Message("", true);
        Long userId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setId(userId);
            userDto.setPassword(encoder.encode(passwordForm.getNewPassword()));
            userMapper.changePassword(userDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_21_I));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setSuccess(false);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_21_E));
        }
        return result;
    }
}
