package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CustomerAddressMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.UserRegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.service.IUserService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    public void createUser(UserRegisterForm userRegisterForm) {
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
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public UserDto getUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    public void updateUser(UserUpdateForm userUpdateForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setUserName(userUpdateForm.getUserName());
            userDto.setEmail(userUpdateForm.getEmail());
            userDto.setFullName(userUpdateForm.getFullName());
            userMapper.updateUser(userDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public List<UserDto> getAllUser() {
        return userMapper.getAllUser();
    }

    public boolean deleteUser(Long userId) {
        UserDto userDto = userMapper.findUserById(userId);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (Consts.ROLE_USER.equals(userDto.getRole())) {
                customerAddressMapper.deleteALLAddressByUser(userId);
                userMapper.deleteUser(userId);
            } else if (Consts.ROLE_VENDOR.equals(userDto.getRole())) {
                userMapper.deleteVendor(userId);
                userMapper.deleteUser(userId);
            } else {
                return false;
            }
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            return false;
        }
        return true;
    }
}
