package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.RegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto user = userMapper.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, user.getAuthorities());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return null;
    }

    @Override
    public void createUser(RegisterForm registerForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setUserName(registerForm.getUserName());
            userDto.setPassword(encoder.encode(registerForm.getPassword()));
            userDto.setEmail(registerForm.getEmail());
            userDto.setFullName(registerForm.getFullName());
            userDto.setProvince(registerForm.getProvince());
            userDto.setRole("ROLE_USER");
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
            userDto.setProvince(userUpdateForm.getProvince());
            userDto.setGender(userUpdateForm.getGender());
            userMapper.updateUser(userDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }

    public List<UserDto> getAllUser() {
        return userMapper.getAllUser();
    }

    public boolean deleteUser(String userName) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userMapper.deleteUser(userName);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            return false;
        }
        return true;
    }
}
